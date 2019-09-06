create PROCEDURE [claims].[archive_inventory_swap](
    @BATCH_SIZE int = 1000,
	@PRESENT_DATE datetime2(7),
	@DAYS_OFFSET  int ,
	@RETURN_VALUE int OUTPUT
)
AS
BEGIN

SET NOCOUNT ON;
SET IMPLICIT_TRANSACTIONS OFF;

SET @RETURN_VALUE = 0;
DECLARE @errorMessage varchar(4000);

IF (@PRESENT_DATE IS NULL) BEGIN
	SET @PRESENT_DATE = SYSDATETIME();
END

DECLARE @MAX_TIME datetime2 = CAST((DATEADD(d, -(@DAYS_OFFSET), @PRESENT_DATE)) AS datetime2);
DECLARE @TotalTranSize bigint = 0;

----
BEGIN TRANSACTION LOG
	INSERT INTO claims_archive.process_log VALUES('Start Of Procedure', 'ARCHIVE_INVENTORY_SWAP_LOAD', 'BEGIN PROCEDURE', NULL, NULL, @PRESENT_DATE, @MAX_TIME, SYSDATETIME());
COMMIT TRANSACTION LOG


----
BEGIN TRANSACTION LOG
	INSERT INTO claims_archive.process_log VALUES('Insert Into [claims_archive].[inventory_swap]', 'ARCHIVE_INVENTORY_SWAP_LOAD', 'START', @TotalTranSize, NULL, NULL, @MAX_TIME, SYSDATETIME());
COMMIT TRANSACTION LOG

BEGIN
	DECLARE @BatchTranSize bigint;

	DECLARE @swap_id int;
	DECLARE @item_nbr bigint;
	DECLARE @club_nbr int;
	DECLARE @item_qty int;
	DECLARE @swap_type varchar(12);
	DECLARE @swap_state varchar(24);
	DECLARE @error_msg varchar(1024);
	DECLARE @created_ts datetime2(7);
	DECLARE @created_user varchar(24);
	DECLARE @last_modified_ts datetime2(7);
	DECLARE @last_modified_user varchar(24);
	DECLARE @item_cost_amt float;
	DECLARE @item_desc varchar(256);
	DECLARE @item_desc2 varchar(256);
	DECLARE @cat_id int;
	DECLARE @alert_msg varchar(2048);
	DECLARE @swap_from varchar(24);
	DECLARE @swap_to varchar(24);
	DECLARE @swap_source varchar(48);
	DECLARE @retryCount int;

	DECLARE TransactionCursor CURSOR
		LOCAL FAST_FORWARD
		FOR SELECT
			i.swap_id,i.item_nbr,i.club_nbr,i.item_qty,i.swap_type,i.swap_state,i.error_msg,i.created_ts,i.created_user,i.last_modified_ts,
			i.last_modified_user,i.item_cost_amt float,i.item_desc,i.item_desc2,i.cat_id,i.alert_msg,i.swap_from,i.swap_to,i.swap_source,i.retry_count
		FROM
			claims.inventory_swap i where i.last_modified_ts<=@MAX_TIME and i.swap_state in ('SWAPPED','SWAP_NOT_NEEDED');

	OPEN TransactionCursor;
	FETCH NEXT FROM TransactionCursor INTO @swap_id,@item_nbr,@club_nbr,@item_qty,@swap_type,@swap_state,@error_msg,@created_ts,@created_user,@last_modified_ts,
		@last_modified_user,@item_cost_amt float,@item_desc,@item_desc2,@cat_id,@alert_msg,@swap_from,@swap_to,@swap_source,@retry_count;

	IF (@@FETCH_STATUS = 0)
		SET @BatchTranSize = 0;

	WHILE (@@FETCH_STATUS = 0) BEGIN
		BEGIN TRY
			BEGIN TRANSACTION LOAD_CATALOG_AUDIT;

			WHILE (@BatchTranSize < @BATCH_SIZE) BEGIN
				IF (@@FETCH_STATUS = 0) BEGIN

					SET @BatchTranSize = @BatchTranSize + 1;
					SET @TotalTranSize = @TotalTranSize + 1;

					insert into claims_archive.inventory_swap1 (audit_id,item_nbr,club_nbr,claim_reason_code,operation,item_qty_old,item_qty_new,open_claim_qty_old,
						open_claim_qty_new,modified_time,modified_user,archived_ts)
					values  (@swap_id,@item_nbr,@club_nbr,@item_qty,@swap_type,@swap_state,@error_msg,@created_ts,@created_user,@last_modified_ts,@last_modified_user,
						@item_cost_amt float,@item_desc,@item_desc2,@cat_id,@alert_msg,@swap_from,@swap_to,@swap_source,@retry_count,SYSDATETIME());
				END
				ELSE BREAK;
				IF (@@ERROR <> 0) BEGIN
					THROW 51000, 'Failed To Insert Into [claims_archive].[inventory_swap]', 1;
				END

				FETCH NEXT FROM TransactionCursor INTO @swap_id,@item_nbr,@club_nbr,@item_qty,@swap_type,@swap_state,@error_msg,@created_ts,@created_user,@last_modified_ts,
					@last_modified_user,@item_cost_amt float,@item_desc,@item_desc2,@cat_id,@alert_msg,@swap_from,@swap_to,@swap_source,@retry_count;
			END
			INSERT INTO claims_archive.process_log VALUES('Batch Insert Into [claims_archive].[inventory_swap]', 'ARCHIVE_INVENTORY_SWAP_LOAD', 'COMMIT BATCH', @TotalTranSize, NULL, NULL, @MAX_TIME, SYSDATETIME());
			SET @BatchTranSize = 0;
			COMMIT TRANSACTION LOAD_CATALOG_AUDIT;
		END TRY
		BEGIN CATCH
			SET @errorMessage = ERROR_MESSAGE();
			IF (@@TRANCOUNT > 0) BEGIN
				ROLLBACK TRANSACTION LOAD_TRANSACTION_CLAIM;
			END
			BEGIN TRANSACTION LOG
				INSERT INTO claims_archive.process_log VALUES('Failed To Insert Into [claims_archive].[inventory_swap]', 'ARCHIVE_INVENTORY_SWAP_LOAD', 'FAILURE', @TotalTranSize,  ERROR_MESSAGE(), NULL, @MAX_TIME, SYSDATETIME());
			COMMIT TRANSACTION LOG
			SET @RETURN_VALUE = -1;
			SELECT @RETURN_VALUE AS 'RETURN_VALUE';
			BREAK;
		END CATCH
	END

	IF (@BatchTranSize = 0) BEGIN
		BEGIN TRANSACTION LOG
			INSERT INTO claims_archive.process_log VALUES('Insert Into [claims_archive].[inventory_swap]', 'ARCHIVE_INVENTORY_SWAP_LOAD', 'SUCCESS', @TotalTranSize, NULL, NULL, @MAX_TIME, SYSDATETIME());
		COMMIT TRANSACTION LOG
		SET @RETURN_VALUE = @TotalTranSize;
	END
	ELSE BEGIN
		BEGIN TRANSACTION LOG
			INSERT INTO claims_archive.process_log VALUES('Insert Into [claims_archive].[inventory_swap]', 'ARCHIVE_INVENTORY_SWAP_LOAD', 'WARNING', @TotalTranSize, 'Initial Fetch Did Not Succeed', NULL, @MAX_TIME, SYSDATETIME());
		COMMIT TRANSACTION LOG
	END

	SET @errorMessage = NULL;
	CLOSE TransactionCursor;
	DEALLOCATE TransactionCursor;
END


SELECT @RETURN_VALUE AS 'RETURN_VALUE';
----
END;
