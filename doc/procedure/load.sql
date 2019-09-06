CREATE PROCEDURE auditarchive.ARCHIVE_LOAD_AUDIT (
	@BATCH_SIZE int = 1000,
	@MIN_TIME dateTime2,
	@MAX_TIME dateTime2,
	@RETURN_VALUE int OUTPUT
)
AS
BEGIN

SET NOCOUNT ON;
SET IMPLICIT_TRANSACTIONS OFF;


SET @RETURN_VALUE = 0;
DECLARE @errorMessage varchar(4000);

DECLARE @TotalTranSize bigint = 0;
DECLARE @TotalItemSize bigint = 0;
DECLARE @TotalRecSize bigint = 0;


----
BEGIN TRANSACTION LOG
	INSERT INTO auditarchive.process_log VALUES('Start Of Procedure', 'ARCHIVE_INITIAL_LOAD', SYSDATETIME(), 'BEGIN PROCEDURE', NULL, NULL, @MIN_TIME, @MAX_TIME);
COMMIT TRANSACTION LOG


----
BEGIN TRANSACTION LOG
	INSERT INTO auditarchive.process_log VALUES('Insert Into [auditarchive].[transactionaudit]', 'ARCHIVE_INITIAL_LOAD', SYSDATETIME(), 'START', @TotalTranSize, NULL, @MIN_TIME, @MAX_TIME);
COMMIT TRANSACTION LOG
----
BEGIN
	DECLARE @BatchTranSize bigint;

	DECLARE @trans_auditid varchar(45);
	DECLARE @trans_auditid_ref bigint;
	DECLARE @trans_clubnbr int;
	DECLARE @trans_associd varchar(40);
	DECLARE @trans_membershipid varchar(20);
	DECLARE @trans_txid varchar(40);
	DECLARE @trans_transtype varchar(10);
	DECLARE @trans_auditstatus bit;
	DECLARE @trans_shrinktype varchar(20);
	DECLARE @trans_scanprompted bit;
	DECLARE @trans_noofitems int;
	DECLARE @trans_ordertotal float;
	DECLARE @trans_createdate datetime2(7);
	DECLARE @trans_auditstartime datetime2(7);
	DECLARE @trans_auditendtime datetime2(7);
	DECLARE @trans_multiauditid bigint;
	DECLARE @trans_deviceoffline bit;
	DECLARE @trans_deviceid varchar(40);
	DECLARE @trans_appversion varchar(20);
	DECLARE @trans_offlinemodetype varchar(20);

	DECLARE TransactionCursor CURSOR
		LOCAL FAST_FORWARD
		FOR SELECT
			SUBSTRING(
				CONCAT(CONVERT(varchar, B.auditid), '_', CONVERT(varchar, B.createdate)),
					0, CHARINDEX('.', CONCAT(CONVERT(varchar, B.auditid), '_', CONVERT(varchar, B.createdate)))
				),
			B.auditid,
	 		B.clubnbr, B.associd, B.membershipid, B.txid, B.transtype, B.auditstatus, B.shrinktype, B.scanprompted, B.noofitems,
			B.ordertotal, B.createdate, B.auditstartime, B.auditendtime, B.multiauditid, B.deviceoffline, B.deviceid, B.appversion, B.offlinemodetype
			FROM audit.transactionaudit B
			WHERE B.createdate BETWEEN @MIN_TIME AND @MAX_TIME;

	OPEN TransactionCursor;
	FETCH NEXT FROM TransactionCursor INTO @trans_auditid, @trans_auditid_ref, @trans_clubnbr, @trans_associd, @trans_membershipid, @trans_txid, @trans_transtype,
	@trans_auditstatus, @trans_shrinktype, @trans_scanprompted, @trans_noofitems, @trans_ordertotal, @trans_createdate, @trans_auditstartime, @trans_auditendtime,
	@trans_multiauditid, @trans_deviceoffline, @trans_deviceid, @trans_appversion, @trans_offlinemodetype;

	IF (@@FETCH_STATUS = 0)
		SET @BatchTranSize = 0;

	WHILE (@@FETCH_STATUS = 0) BEGIN
		BEGIN TRY
			BEGIN TRANSACTION LOAD_TRANSACTIONAUDIT;

			WHILE (@BatchTranSize < @BATCH_SIZE) BEGIN
				IF (@@FETCH_STATUS = 0) BEGIN

					SET @BatchTranSize = @BatchTranSize + 1;
					SET @TotalTranSize = @TotalTranSize + 1;

					INSERT INTO auditarchive.transactionaudit (auditid, auditid_ref, clubnbr, associd, membershipid, txid, transtype, auditstatus, shrinktype,
						scanprompted, noofitems, ordertotal, createdate, auditstartime, auditendtime, multiauditid, deviceoffline, deviceid, appversion, offlinemodetype)
					VALUES (@trans_auditid, @trans_auditid_ref, @trans_clubnbr, @trans_associd, @trans_membershipid, @trans_txid, @trans_transtype, @trans_auditstatus, @trans_shrinktype, @trans_scanprompted,
						@trans_noofitems, @trans_ordertotal, @trans_createdate, @trans_auditstartime, @trans_auditendtime, @trans_multiauditid, @trans_deviceoffline, @trans_deviceid, @trans_appversion, @trans_offlinemodetype);

				END
				ELSE BREAK;
				IF (@@ERROR <> 0) BEGIN
					THROW 51000, 'Failed To Insert Into [auditarchive].[transactionaudit]', 1;
				END

				FETCH NEXT FROM TransactionCursor INTO @trans_auditid, @trans_auditid_ref, @trans_clubnbr, @trans_associd, @trans_membershipid, @trans_txid, @trans_transtype, @trans_auditstatus, @trans_shrinktype,
					@trans_scanprompted, @trans_noofitems, @trans_ordertotal, @trans_createdate, @trans_auditstartime, @trans_auditendtime, @trans_multiauditid, @trans_deviceoffline, @trans_deviceid, @trans_appversion, @trans_offlinemodetype;
				IF (@@ERROR <> 0) BEGIN
					THROW 51000, 'Batch Failed In [auditarchive].[transactionaudit]', 1;
				END
			END

			SET @BatchTranSize = 0;
			INSERT INTO auditarchive.process_log VALUES('Batch Insert Into [auditarchive].[transactionaudit]', 'ARCHIVE_INITIAL_LOAD', SYSDATETIME(), 'COMMIT BATCH', @TotalTranSize, NULL, @MIN_TIME, @MAX_TIME);
			COMMIT TRANSACTION LOAD_TRANSACTIONAUDIT;
		END TRY
		BEGIN CATCH
			SET @errorMessage = ERROR_MESSAGE();
			IF (@@TRANCOUNT > 0) BEGIN
				ROLLBACK TRANSACTION LOAD_TRANSACTIONAUDIT;
			END
			BEGIN TRANSACTION LOG
				INSERT INTO auditarchive.process_log VALUES('Failed To Insert Into [auditarchive].[transactionaudit]', 'ARCHIVE_INITIAL_LOAD', SYSDATETIME(), 'FAILURE', @TotalTranSize,  ERROR_MESSAGE(), @MIN_TIME, @MAX_TIME);
			COMMIT TRANSACTION LOG
			SET @RETURN_VALUE = -1;
			SELECT @RETURN_VALUE AS 'RETURN_VALUE';
			BREAK;
		END CATCH
	END

	IF (@BatchTranSize = 0) BEGIN
		BEGIN TRANSACTION LOG
			INSERT INTO auditarchive.process_log VALUES('Insert Into [auditarchive].[transactionaudit]', 'ARCHIVE_INITIAL_LOAD', SYSDATETIME(), 'SUCCESS', @TotalTranSize, NULL, @MIN_TIME, @MAX_TIME);
		COMMIT TRANSACTION LOG
		SET @RETURN_VALUE = @TotalTranSize;
	END
	ELSE BEGIN
		BEGIN TRANSACTION LOG
			INSERT INTO auditarchive.process_log VALUES('Insert Into [auditarchive].[transactionaudit]', 'ARCHIVE_INITIAL_LOAD', SYSDATETIME(), 'WARNING', @TotalTranSize, 'Initial Fetch Did Not Succeed', @MIN_TIME, @MAX_TIME);
		COMMIT TRANSACTION LOG
	END

	SET @errorMessage = NULL;
	CLOSE TransactionCursor;
	DEALLOCATE TransactionCursor;
END


----
BEGIN TRANSACTION LOG
	INSERT INTO auditarchive.process_log VALUES('Insert Into [auditarchive].[itemsaudit]', 'ARCHIVE_INITIAL_LOAD', SYSDATETIME(), 'START', @TotalItemSize, NULL, @MIN_TIME, @MAX_TIME);
COMMIT TRANSACTION LOG
----
BEGIN
	DECLARE @BatchItemSize bigint;

	DECLARE @item_audititemid varchar(45);
	DECLARE @item_audititemid_ref bigint;
	DECLARE @item_auditid bigint;
	DECLARE @item_upc varchar(20);
	DECLARE @item_itemnbr int;
	DECLARE @item_txqty int;
	DECLARE @item_displayname varchar(40);
	DECLARE @item_unitprice float;
	DECLARE @item_createdate datetime2(7);
	DECLARE @item_scannedqty int;
	DECLARE @item_cancelled bit;

	DECLARE ItemCursor CURSOR
		LOCAL FAST_FORWARD
		FOR SELECT
			SUBSTRING(
				CONCAT(CONVERT(varchar, B.audititemid), '_', CONVERT(varchar, B.createdate)),
				0, CHARINDEX('.', CONCAT(CONVERT(varchar, B.audititemid), '_', CONVERT(varchar, B.createdate)))
			),
			B.audititemid,
			C.auditid,
			B.upc, B.itemnbr, B.txqty, B.displayname, B.unitprice, B.createdate, B.scannedqty, B.cancelled
			FROM audit.itemsaudit B
			JOIN audit.transactionaudit C
			ON B.auditid = C.auditid
			WHERE C.createdate BETWEEN @MIN_TIME AND @MAX_TIME;

	OPEN ItemCursor;

	FETCH NEXT FROM ItemCursor INTO @item_audititemid, @item_audititemid_ref, @item_auditid, @item_upc, @item_itemnbr, @item_txqty, @item_displayname, @item_unitprice, @item_createdate, @item_scannedqty, @item_cancelled;

	IF (@@FETCH_STATUS = 0)
		SET @BatchItemSize = 0;

	WHILE (@@FETCH_STATUS = 0) BEGIN
		BEGIN TRY
			BEGIN TRANSACTION LOAD_ITEMSAUDIT;

			WHILE (@BatchItemSize < @BATCH_SIZE) BEGIN
				IF (@@FETCH_STATUS = 0) BEGIN

					SET @BatchItemSize = @BatchItemSize + 1;
					SET @TotalItemSize = @TotalItemSize + 1;

					INSERT INTO auditarchive.itemsaudit (audititemid, audititemid_ref, auditid, upc, itemnbr, txqty, displayname, unitprice, createdate, scannedqty, cancelled)
					VALUES (@item_audititemid, @item_audititemid_ref, @item_auditid, @item_upc, @item_itemnbr, @item_txqty, @item_displayname, @item_unitprice, @item_createdate, @item_scannedqty, @item_cancelled);

				END
				ELSE BREAK;
				IF (@@ERROR <> 0) BEGIN
					THROW 51000, 'Failed To Insert Into [auditarchive].[itemsaudit]', 1;
				END

				FETCH NEXT FROM ItemCursor INTO @item_audititemid, @item_audititemid_ref, @item_auditid, @item_upc, @item_itemnbr, @item_txqty, @item_displayname, @item_unitprice, @item_createdate, @item_scannedqty, @item_cancelled;
				IF (@@ERROR <> 0) BEGIN
					THROW 51000, 'Batch Failed In [auditarchive].[itemsaudit]', 1;
				END
			END

			SET @BatchItemSize = 0;
			INSERT INTO auditarchive.process_log VALUES('Batch Insert Into [auditarchive].[itemsaudit]', 'ARCHIVE_INITIAL_LOAD', SYSDATETIME(), 'COMMIT BATCH', @TotalItemSize, NULL, @MIN_TIME, @MAX_TIME);
			COMMIT TRANSACTION LOAD_ITEMSAUDIT;
		END TRY
		BEGIN CATCH
			SET @errorMessage = ERROR_MESSAGE();
			IF (@@TRANCOUNT > 0) BEGIN
				ROLLBACK TRANSACTION LOAD_ITEMSAUDIT;
			END
			BEGIN TRANSACTION LOG
				INSERT INTO auditarchive.process_log VALUES('Failed To Insert Into [auditarchive].[itemsaudit]', 'ARCHIVE_INITIAL_LOAD', SYSDATETIME(), 'FAILURE', @TotalItemSize,  ERROR_MESSAGE(), @MIN_TIME, @MAX_TIME);
			COMMIT TRANSACTION LOG
			SET @RETURN_VALUE = -1;
			SELECT @RETURN_VALUE AS 'RETURN_VALUE';
			BREAK;
		END CATCH
	END

	IF (@BatchItemSize = 0) BEGIN
		BEGIN TRANSACTION LOG
			INSERT INTO auditarchive.process_log VALUES('Insert Into [auditarchive].[itemsaudit]', 'ARCHIVE_INITIAL_LOAD', SYSDATETIME(), 'SUCCESS', @TotalItemSize, NULL, @MIN_TIME, @MAX_TIME);
		COMMIT TRANSACTION LOG
	END
	ELSE BEGIN
		BEGIN TRANSACTION LOG
			INSERT INTO auditarchive.process_log VALUES('Insert Into [auditarchive].[itemsaudit]', 'ARCHIVE_INITIAL_LOAD', SYSDATETIME(), 'WARNING', @TotalItemSize, 'Initial Fetch Did Not Succeed', @MIN_TIME, @MAX_TIME);
		COMMIT TRANSACTION LOG
	END

	SET @errorMessage = NULL;
	CLOSE ItemCursor;
	DEALLOCATE ItemCursor;
END


----
BEGIN TRANSACTION LOG
	INSERT INTO auditarchive.process_log VALUES('Insert Into [auditarchive].[recommendations]', 'ARCHIVE_INITIAL_LOAD', SYSDATETIME(), 'START', @TotalRecSize, NULL, @MIN_TIME, @MAX_TIME);
COMMIT TRANSACTION LOG
----
BEGIN
	DECLARE @BatchRecSize bigint;

	DECLARE @rec_recid varchar(45);
	DECLARE @rec_recid_ref bigint;
	DECLARE @rec_auditid bigint;
	DECLARE @rec_rec_source varchar(20);
	DECLARE @rec_rec_details varchar(256);
	DECLARE @rec_itemnbr int;
	DECLARE @rec_upc varchar(20);
	DECLARE @rec_qty int;
	DECLARE @rec_createdate datetime2(7);

	DECLARE RecCursor CURSOR
		LOCAL FAST_FORWARD
		FOR SELECT
			SUBSTRING(
				CONCAT(CONVERT(varchar, B.recid), '_', CONVERT(varchar, B.createdate)),
					0, CHARINDEX('.', CONCAT(CONVERT(varchar, B.recid), '_', CONVERT(varchar, B.createdate)))
				),
			B.recid,
			C.auditid,
			B.rec_source, B.rec_details, B.itemnbr, B.upc, B.qty, B.createdate
			FROM audit.recommendations B
			JOIN audit.transactionaudit C
			ON B.auditid = C.auditid
			WHERE C.createdate BETWEEN @MIN_TIME AND @MAX_TIME;

	OPEN RecCursor;

	FETCH NEXT FROM RecCursor INTO @rec_recid, @rec_recid_ref, @rec_auditid, @rec_rec_source, @rec_rec_details, @rec_itemnbr, @rec_upc, @rec_qty, @rec_createdate;

	IF (@@FETCH_STATUS = 0)
		SET @BatchRecSize = 0;

	WHILE (@@FETCH_STATUS = 0) BEGIN
		BEGIN TRY
			BEGIN TRANSACTION LOAD_RECOMMENDATIONS;

			WHILE (@BatchRecSize < @BATCH_SIZE) BEGIN
				IF (@@FETCH_STATUS = 0) BEGIN

					SET @BatchRecSize = @BatchRecSize + 1;
					SET @TotalRecSize = @TotalRecSize + 1;

					INSERT INTO auditarchive.recommendations (recid, recid_ref, auditid, rec_source, rec_details, itemnbr, upc, qty, createdate)
					VALUES (@rec_recid, @rec_recid_ref, @rec_auditid, @rec_rec_source, @rec_rec_details, @rec_itemnbr, @rec_upc, @rec_qty, @rec_createdate);

				END
				ELSE BREAK;
				IF (@@ERROR <> 0) BEGIN
					THROW 51000, 'Failed To Insert Into [auditarchive].[recommendations]', 1;
				END

				FETCH NEXT FROM RecCursor INTO @rec_recid, @rec_recid_ref, @rec_auditid, @rec_rec_source, @rec_rec_details, @rec_itemnbr, @rec_upc, @rec_qty, @rec_createdate;
				IF (@@ERROR <> 0) BEGIN
					THROW 51000, 'Batch Failed In [auditarchive].[recommendations]', 1;
				END
			END

			SET @BatchRecSize = 0;
			INSERT INTO auditarchive.process_log VALUES('Batch Insert Into [auditarchive].[recommendations]', 'ARCHIVE_INITIAL_LOAD', SYSDATETIME(), 'COMMIT BATCH', @TotalRecSize, NULL, @MIN_TIME, @MAX_TIME);
			COMMIT TRANSACTION LOAD_RECOMMENDATIONS;
		END TRY
		BEGIN CATCH
			SET @errorMessage = ERROR_MESSAGE();
			IF (@@TRANCOUNT > 0) BEGIN
				ROLLBACK TRANSACTION LOAD_RECOMMENDATIONS;
			END
			BEGIN TRANSACTION LOG
				INSERT INTO auditarchive.process_log VALUES('Failed To Insert Into [auditarchive].[recommendations]', 'ARCHIVE_INITIAL_LOAD', SYSDATETIME(), 'FAILURE', @TotalRecSize,  ERROR_MESSAGE(), @MIN_TIME, @MAX_TIME);
			COMMIT TRANSACTION LOG
			SET @RETURN_VALUE = -1;
			BREAK;
		END CATCH
	END

	IF (@BatchRecSize = 0) BEGIN
		BEGIN TRANSACTION LOG
			INSERT INTO auditarchive.process_log VALUES('Insert Into [auditarchive].[recommendations]', 'ARCHIVE_INITIAL_LOAD', SYSDATETIME(), 'SUCCESS', @TotalRecSize, NULL, @MIN_TIME, @MAX_TIME);
		COMMIT TRANSACTION LOG
	END
	ELSE BEGIN
		BEGIN TRANSACTION LOG
			INSERT INTO auditarchive.process_log VALUES('Insert Into [auditarchive].[recommendations]', 'ARCHIVE_INITIAL_LOAD', SYSDATETIME(), 'WARNING', @TotalRecSize, 'Initial Fetch Did Not Succeed', @MIN_TIME, @MAX_TIME);
		COMMIT TRANSACTION LOG
	END

	SET @errorMessage = NULL;
	CLOSE RecCursor;
	DEALLOCATE RecCursor;
END


----
IF (@RETURN_VALUE >= 0) BEGIN
	BEGIN TRANSACTION LOG
		INSERT INTO auditarchive.process_log VALUES('End Of Procedure', 'ARCHIVE_INITIAL_LOAD', SYSDATETIME(), 'PROCEDURE SUCCEEDED', NULL, NULL, @MIN_TIME, @MAX_TIME);
	COMMIT TRANSACTION LOG
END
ELSE BEGIN
	BEGIN TRANSACTION LOG
		INSERT INTO auditarchive.process_log VALUES('End Of Procedure', 'ARCHIVE_INITIAL_LOAD', SYSDATETIME(), 'PROCEDURE FAILED', NULL, @errorMessage, @MIN_TIME, @MAX_TIME);
	COMMIT TRANSACTION LOG
END


----
SELECT @RETURN_VALUE AS 'RETURN_VALUE';


----
END;
