ALTER PROCEDURE [claims].[archiveClaims](
    @BATCH_SIZE int = 1000,
	@PRESENT_DATE datetime2(7),
	@DAYS_OFFSET  int = 2,
	@RETURN_VALUE int OUTPUT
)
AS
BEGIN

SET @RETURN_VALUE = 0;
DECLARE @errorMessage varchar(4000);

IF (@PRESENT_DATE IS NULL) BEGIN
	SET @PRESENT_DATE = SYSDATETIME(); --2019-08-29 06:28:01.3554111
END

DECLARE @MAX_TIME datetime2 = CAST((DATEADD(d, -(@DAYS_OFFSET), @PRESENT_DATE)) AS datetime2);
DECLARE @TotalTranSize bigint = 0;
DECLARE @TotalItemSize bigint = 0;

print ('@PRESENT_DATE:'+convert(nvarchar(50),@PRESENT_DATE)+' @MAX_TIME:'+convert(nvarchar(50),@MAX_TIME))



----
BEGIN TRANSACTION LOG
	INSERT INTO claims_archive.process_log VALUES('Start Of Procedure', 'ARCHIVE_ClAIM_LOAD', 'BEGIN PROCEDURE', NULL, NULL, @PRESENT_DATE, @MAX_TIME, SYSDATETIME());
COMMIT TRANSACTION LOG


----
BEGIN TRANSACTION LOG
	INSERT INTO claims_archive.process_log VALUES('Insert Into [claims_archive].[claim]', 'ARCHIVE_ClAIM_LOAD', 'START', @TotalTranSize, NULL, NULL, @MAX_TIME, SYSDATETIME());
COMMIT TRANSACTION LOG

BEGIN
	DECLARE @BatchTranSize bigint;

	DECLARE @trans_claimId int;
	DECLARE @trans_clubNbr int;
	DECLARE @trans_claimName varchar(40);
	DECLARE @trans_claimSeqNbr varchar(4);
	DECLARE @trans_claimType varchar(10);
	DECLARE @trans_reasonCode varchar(10);
	DECLARE @trans_claimDispositionTypeCode varchar(10);
	DECLARE @trans_storeNotesTxt varchar(512);
	DECLARE @trans_returnAuthNbr varchar(24);
	DECLARE @trans_claimWeightQuantity float;
	DECLARE @trans_shipChargeAmount float;
	DECLARE @trans_lastModifiedTs datetime2(7);
	DECLARE @trans_lastModifiedUser varchar(24);
	DECLARE @trans_claimOpenTs datetime2(7);
	DECLARE @trans_createUserid varchar(24);
	DECLARE @trans_state varchar(24);
	DECLARE @trans_vendorName varchar(20);
	DECLARE @trans_vendorNbr  int;
	DECLARE @trans_vendorDeptNbr int;
	DECLARE @trans_hazmatId bigint;
	DECLARE @trans_trackingNbr varchar(256);
	DECLARE @trans_boxAudited bit;
	DECLARE @trans_boxAuditedUser varchar(24);
	DECLARE @trans_boxAuditedTs datetime2(7);
	DECLARE @trans_recallId int;
	DECLARE @trans_recallEndDate datetime2(7);
	DECLARE @trans_claimShipmentId bigint;
	DECLARE @trans_palletId bigint;
	DECLARE @trans_packingInstructions varchar(10);

	DECLARE TransactionCursor CURSOR
		LOCAL FAST_FORWARD
		FOR SELECT
			c.claim_id,c.club_nbr,c.claim_name,c.claim_sequence_nbr,c.claim_type_code,c.claim_reason_code,c.claim_disposition_type_code,
			c.store_notes_txt,c.return_auth_nbr,c.claim_weight,c.ship_charge_amt,c.last_modified_ts,c.last_modified_user,
			c.claim_open_ts,c.created_user,c.state,c.vendor_name,c.vendor_nbr,c.vendor_dept_nbr,
			c.hazmat_id,c.tracking_nbr,c.box_audited,c.box_audited_user,c.box_audited_ts,c.recall_id,
			c.recall_end_date,c.claim_shipment_id,c.pallet_id,c.packing_instr
		FROM
			claims.claim c where c.club_nbr=4969;

	OPEN TransactionCursor;
	FETCH NEXT FROM TransactionCursor INTO @trans_claimId, @trans_clubNbr, @trans_claimName, @trans_claimSeqNbr, @trans_claimType, @trans_reasonCode, @trans_claimDispositionTypeCode,
	@trans_storeNotesTxt, @trans_returnAuthNbr, @trans_claimWeightQuantity, @trans_shipChargeAmount, @trans_lastModifiedTs, @trans_lastModifiedUser, @trans_claimOpenTs, @trans_createUserid,
	@trans_state, @trans_vendorName, @trans_vendorNbr, @trans_vendorDeptNbr, @trans_hazmatId, @trans_trackingNbr, @trans_boxAudited, @trans_boxAuditedUser, @trans_boxAuditedTs,
	@trans_recallId, @trans_recallEndDate, @trans_claimShipmentId, @trans_palletId, @trans_packingInstructions;

	IF (@@FETCH_STATUS = 0)
		SET @BatchTranSize = 0;

	WHILE (@@FETCH_STATUS = 0) BEGIN
		BEGIN TRY
			BEGIN TRANSACTION LOAD_TRANSACTION_CLAIM;

			WHILE (@BatchTranSize < @BATCH_SIZE) BEGIN
				IF (@@FETCH_STATUS = 0) BEGIN

					SET @BatchTranSize = @BatchTranSize + 1;
					SET @TotalTranSize = @TotalTranSize + 1;

					print @trans_claimId;
					print @trans_clubNbr;

					insert into claims_archive.claim1 (claim_id,club_nbr,claim_name,claim_sequence_nbr,claim_type_code,claim_reason_code,claim_disposition_type_code,store_notes_txt,return_auth_nbr,
					claim_weight,ship_charge_amt,last_modified_ts,last_modified_user,claim_open_ts,created_user,state,vendor_name,vendor_nbr,vendor_dept_nbr,hazmat_id,tracking_nbr,box_audited,
					box_audited_user,box_audited_ts,recall_id,recall_end_date,claim_shipment_id,pallet_id,packing_instr,archived_ts)
					values (@trans_claimId, @trans_clubNbr, @trans_claimName, @trans_claimSeqNbr, @trans_claimType, @trans_reasonCode, @trans_claimDispositionTypeCode,
					@trans_storeNotesTxt, @trans_returnAuthNbr, @trans_claimWeightQuantity, @trans_shipChargeAmount, @trans_lastModifiedTs, @trans_lastModifiedUser, @trans_claimOpenTs, @trans_createUserid,
					@trans_state, @trans_vendorName, @trans_vendorNbr, @trans_vendorDeptNbr, @trans_hazmatId, @trans_trackingNbr, @trans_boxAudited, @trans_boxAuditedUser, @trans_boxAuditedTs,
					@trans_recallId, @trans_recallEndDate, @trans_claimShipmentId, @trans_palletId, @trans_packingInstructions,SYSDATETIME())
				END
				ELSE BREAK;
				IF (@@ERROR <> 0) BEGIN
					THROW 51000, 'Failed To Insert Into [claims_archive].[claim]', 1;
				END

				FETCH NEXT FROM TransactionCursor INTO @trans_claimId, @trans_clubNbr, @trans_claimName, @trans_claimSeqNbr, @trans_claimType, @trans_reasonCode, @trans_claimDispositionTypeCode,
				@trans_storeNotesTxt, @trans_returnAuthNbr, @trans_claimWeightQuantity, @trans_shipChargeAmount, @trans_lastModifiedTs, @trans_lastModifiedUser, @trans_claimOpenTs, @trans_createUserid,
				@trans_state, @trans_vendorName, @trans_vendorNbr, @trans_vendorDeptNbr, @trans_hazmatId, @trans_trackingNbr, @trans_boxAudited, @trans_boxAuditedUser, @trans_boxAuditedTs,
				@trans_recallId, @trans_recallEndDate, @trans_claimShipmentId, @trans_palletId, @trans_packingInstructions;
			END
			SET @BatchTranSize = 0;
			INSERT INTO claims_archive.process_log VALUES('Batch Insert Into [claims_archive].[claim]', 'ARCHIVE_CLAIM_LOAD', 'COMMIT BATCH', @TotalTranSize, NULL, NULL, @MAX_TIME, SYSDATETIME());
			COMMIT TRANSACTION LOAD_TRANSACTIONAUDIT;
		END TRY
		BEGIN CATCH
			SET @errorMessage = ERROR_MESSAGE();
			IF (@@TRANCOUNT > 0) BEGIN
				ROLLBACK TRANSACTION LOAD_TRANSACTION_CLAIM;
			END
			BEGIN TRANSACTION LOG
				INSERT INTO claims_archive.process_log VALUES('Failed To Insert Into [claims_archive].[claim]', 'ARCHIVE_CLAIM_LOAD', 'FAILURE', @TotalTranSize,  ERROR_MESSAGE(), NULL, @MAX_TIME, SYSDATETIME());
			COMMIT TRANSACTION LOG
			SET @RETURN_VALUE = -1;
			SELECT @RETURN_VALUE AS 'RETURN_VALUE';
			BREAK;
		END CATCH
	END

	IF (@BatchTranSize = 0) BEGIN
		BEGIN TRANSACTION LOG
			INSERT INTO claims_archive.process_log VALUES('Insert Into [claims_archive].[claim]', 'ARCHIVE_CLAIM_LOAD', 'SUCCESS', @TotalTranSize, NULL, NULL, @MAX_TIME, SYSDATETIME());
		COMMIT TRANSACTION LOG
		SET @RETURN_VALUE = @TotalTranSize;
	END
	ELSE BEGIN
		BEGIN TRANSACTION LOG
			INSERT INTO claims_archive.process_log VALUES('Insert Into [claims_archive].[claim]', 'ARCHIVE_CLAIM_LOAD', 'WARNING', @TotalTranSize, 'Initial Fetch Did Not Succeed', NULL, @MAX_TIME, SYSDATETIME());
		COMMIT TRANSACTION LOG
	END

	SET @errorMessage = NULL;
	CLOSE TransactionCursor;
	DEALLOCATE TransactionCursor;

END

---
----
BEGIN TRANSACTION LOG
	INSERT INTO claims_archive.process_log VALUES('Start Of Procedure', 'ARCHIVE_ITEM_LOAD', 'BEGIN PROCEDURE', NULL, NULL, NULL, @MAX_TIME, SYSDATETIME());
COMMIT TRANSACTION LOG




BEGIN TRANSACTION LOG
	INSERT INTO claims_archive.process_log VALUES('Insert Into [claims_archive].[claim_item]', 'ARCHIVE_ITEM_LOAD', 'START', @TotalItemSize, NULL, NULL, @MAX_TIME, SYSDATETIME());
COMMIT TRANSACTION LOG

BEGIN
	DECLARE @BatchItemSize bigint;

	DECLARE @claim_item_id bigint;
	DECLARE @item_nbr bigint;
	DECLARE @club_nbr int;
	DECLARE @upc_nbr varchar(16);
	DECLARE @gtin_nbr varchar(16);
	DECLARE @cat_id int;
	DECLARE @item_desc varchar(256);
	DECLARE @item_desc2 varchar(256);
	DECLARE @vendor_name varchar(256);
	DECLARE @vendor_nbr int;
	DECLARE @vendor_seq_nbr int;
	DECLARE @vendor_dept_nbr int;
	DECLARE @item_qty int;
	DECLARE @virt_qty int;
	DECLARE @item_cost_amt float;
	DECLARE @item_sell_amt float;
	DECLARE @item_retail_amt float;
	DECLARE @vendor_pack_qty int;
	DECLARE @vendor_pack_amt float;
	DECLARE @vendor_credit_amt float;
	DECLARE @handling_charge_amt float;
	DECLARE @disposal_charge_amt float;
	DECLARE @claim_id bigint;
	DECLARE @bucket_id int;
	DECLARE @bucket_description varchar(24);
	DECLARE @hazmat_id bigint;
	DECLARE @stock_number varchar(256);
	DECLARE @hazwaste int;
	DECLARE @bucket_color varchar(12);
	DECLARE @damage_type varchar(24);
	DECLARE @damage_reason varchar(256);
	DECLARE @created_user varchar(24);
	DECLARE @created_ts datetime2(7);
	DECLARE @last_modified_user varchar(24);
	DECLARE @last_modified_ts datetime2(7);
	DECLARE @cm_rules varchar(2048);
	DECLARE @dot_haz_class_code varchar(12);
	DECLARE @dot_haz_class_code_desc varchar(48);
	DECLARE @dot_reg_code varchar(24);
	DECLARE @dot_hazmat_nbr varchar(12);
	DECLARE @shipping_name varchar(254);
	DECLARE @limited_qty float;
	DECLARE @dot_packing_code varchar(12);
	DECLARE @dot_exemption_nbr varchar(24);
	DECLARE @flash_point_amt varchar(12);
	DECLARE @flash_point_uom varchar(12);
	DECLARE @marine_pollute_ind bit;
	DECLARE @is_backfeed_fail bit;
	DECLARE @is_deleted_on_corr bit;
	DECLARE @realtime_swap_qty int;
	DECLARE @battery_label varchar(24);
	DECLARE @packing_instr varchar(8);
	DECLARE @dot_haz_class_cd_orig varchar(12);
	DECLARE @is_rctr_disp bit;
	--DECLARE @recall_end_date datetime2(7);
	DECLARE @alert_msg varchar(2048);

	DECLARE ItemCursor CURSOR
		LOCAL FAST_FORWARD
		FOR SELECT
				i.claim_item_id,i.item_nbr,i.club_nbr,i.upc_nbr,i.gtin_nbr,i.cat_id,i.item_desc,i.item_desc2,i.vendor_name,i.vendor_nbr,i.vendor_seq_nbr,i.vendor_dept_nbr,i.item_qty,i.virt_qty,i.item_cost_amt,i.item_sell_amt,i.item_retail_amt,i.
				vendor_pack_qty,i.vendor_pack_amt,i.vendor_credit_amt,i.handling_charge_amt,i.disposal_charge_amt,i.claim_id,i.bucket_id,i.bucket_description,i.hazmat_id,i.stock_number,i.hazwaste,i.bucket_color,i.damage_type,i.damage_reason,i.created_user,i.
				created_ts,i.last_modified_user,i.last_modified_ts,i.cm_rules,i.dot_haz_class_code,i.dot_haz_class_code_desc,i.dot_reg_code,i.dot_hazmat_nbr,i.shipping_name,i.limited_qty,i.dot_packing_code,i.dot_exemption_nbr,i.flash_point_amt,i.
				flash_point_uom,i.marine_pollute_ind,i.is_backfeed_fail,i.is_deleted_on_corr,i.realtime_swap_qty,i.battery_label,i.packing_instr,i.dot_haz_class_cd_orig,i.is_rctr_disp,i.alert_msg
			FROM claims.claim_item i INNER JOIN claims.claim c
			ON i.claim_id =c.claim_id
			WHERE i.club_nbr=4969;

	OPEN ItemCursor;

	FETCH NEXT FROM ItemCursor INTO
				@claim_item_id,@item_nbr,@club_nbr,@upc_nbr,@gtin_nbr,@cat_id,@item_desc,@item_desc2,@vendor_name,@vendor_nbr,@vendor_seq_nbr,@vendor_dept_nbr,@item_qty,@virt_qty,@item_cost_amt,@item_sell_amt,@item_retail_amt,
				@vendor_pack_qty,@vendor_pack_amt,@vendor_credit_amt,@handling_charge_amt,@disposal_charge_amt,@claim_id,@bucket_id,@bucket_description,@hazmat_id,@stock_number,@hazwaste,@bucket_color,@damage_type,@damage_reason,@created_user,
				@created_ts,@last_modified_user,@last_modified_ts,@cm_rules,@dot_haz_class_code,@dot_haz_class_code_desc,@dot_reg_code,@dot_hazmat_nbr,@shipping_name,@limited_qty,@dot_packing_code,@dot_exemption_nbr,@flash_point_amt,
				@flash_point_uom,@marine_pollute_ind,@is_backfeed_fail,@is_deleted_on_corr,@realtime_swap_qty,@battery_label,@packing_instr,@dot_haz_class_cd_orig,@is_rctr_disp,@alert_msg;
	IF (@@FETCH_STATUS = 0)
		SET @BatchItemSize = 0;

	WHILE (@@FETCH_STATUS = 0) BEGIN
		BEGIN TRY
			BEGIN TRANSACTION LOAD_ITEMS;

			WHILE (@BatchItemSize < @BATCH_SIZE) BEGIN
				IF (@@FETCH_STATUS = 0) BEGIN

					SET @BatchItemSize = @BatchItemSize + 1;
					SET @TotalItemSize = @TotalItemSize + 1;

					INSERT INTO claims_archive.claim_item1 (claim_item_id,item_nbr,club_nbr,upc_nbr,gtin_nbr,cat_id,item_desc,item_desc2,vendor_name,vendor_nbr,vendor_seq_nbr,vendor_dept_nbr,item_qty,virt_qty,item_cost_amt,item_sell_amt,item_retail_amt,
						vendor_pack_qty,vendor_pack_amt,vendor_credit_amt,handling_charge_amt,disposal_charge_amt,claim_id,bucket_id,bucket_description,hazmat_id,stock_number,hazwaste,bucket_color,damage_type,damage_reason,created_user,
						created_ts,last_modified_user,last_modified_ts,cm_rules,dot_haz_class_code,dot_haz_class_code_desc,dot_reg_code,dot_hazmat_nbr,shipping_name,limited_qty,dot_packing_code,dot_exemption_nbr,flash_point_amt,
						flash_point_uom,marine_pollute_ind,is_backfeed_fail,is_deleted_on_corr,realtime_swap_qty,battery_label,packing_instr,dot_haz_class_cd_orig,is_rctr_disp,alert_msg,archived_ts)
					VALUES (@claim_item_id,@item_nbr,@club_nbr,@upc_nbr,@gtin_nbr,@cat_id,@item_desc,@item_desc2,@vendor_name,@vendor_nbr,@vendor_seq_nbr,@vendor_dept_nbr,@item_qty,@virt_qty,@item_cost_amt,@item_sell_amt,@item_retail_amt,
						@vendor_pack_qty,@vendor_pack_amt,@vendor_credit_amt,@handling_charge_amt,@disposal_charge_amt,@claim_id,@bucket_id,@bucket_description,@hazmat_id,@stock_number,@hazwaste,@bucket_color,@damage_type,@damage_reason,@created_user,
						@created_ts,@last_modified_user,@last_modified_ts,@cm_rules,@dot_haz_class_code,@dot_haz_class_code_desc,@dot_reg_code,@dot_hazmat_nbr,@shipping_name,@limited_qty,@dot_packing_code,@dot_exemption_nbr,@flash_point_amt,
						@flash_point_uom,@marine_pollute_ind,@is_backfeed_fail,@is_deleted_on_corr,@realtime_swap_qty,@battery_label,@packing_instr,@dot_haz_class_cd_orig,@is_rctr_disp,@alert_msg,SYSDATETIME());

				END
				ELSE BREAK;
				IF (@@ERROR <> 0) BEGIN
					THROW 51000, 'Failed To Insert Into [claims_archive].[claim_item]', 1;
				END

				FETCH NEXT FROM ItemCursor INTO
					@claim_item_id,@item_nbr,@club_nbr,@upc_nbr,@gtin_nbr,@cat_id,@item_desc,@item_desc2,@vendor_name,@vendor_nbr,@vendor_seq_nbr,@vendor_dept_nbr,@item_qty,@virt_qty,@item_cost_amt,@item_sell_amt,@item_retail_amt,
					@vendor_pack_qty,@vendor_pack_amt,@vendor_credit_amt,@handling_charge_amt,@disposal_charge_amt,@claim_id,@bucket_id,@bucket_description,@hazmat_id,@stock_number,@hazwaste,@bucket_color,@damage_type,@damage_reason,@created_user,
					@created_ts,@last_modified_user,@last_modified_ts,@cm_rules,@dot_haz_class_code,@dot_haz_class_code_desc,@dot_reg_code,@dot_hazmat_nbr,@shipping_name,@limited_qty,@dot_packing_code,@dot_exemption_nbr,@flash_point_amt,
					@flash_point_uom,@marine_pollute_ind,@is_backfeed_fail,@is_deleted_on_corr,@realtime_swap_qty,@battery_label,@packing_instr,@dot_haz_class_cd_orig,@is_rctr_disp,@alert_msg
				IF (@@ERROR <> 0) BEGIN
					THROW 51000, 'Batch Failed In [claims_archive].[claim_item]', 1;
				END
			END

			SET @BatchItemSize = 0;
			INSERT INTO claims_archive.process_log VALUES('Batch Insert Into [claims_archive].[claim_item]', 'ARCHIVE_ITEM_LOAD', 'COMMIT BATCH', @TotalItemSize, NULL, NULL, @MAX_TIME, SYSDATETIME());
			COMMIT TRANSACTION LOAD_ITEMS;
		END TRY
		BEGIN CATCH
			SET @errorMessage = ERROR_MESSAGE();
			IF (@@TRANCOUNT > 0) BEGIN
				ROLLBACK TRANSACTION LOAD_ITEMS;
			END
			BEGIN TRANSACTION LOG
				INSERT INTO claims_archive.process_log VALUES('Failed To Insert Into [claims_archive].[claim_item]', 'ARCHIVE_ITEM_LOAD', 'FAILURE', @TotalItemSize,  ERROR_MESSAGE(), NULL, @MAX_TIME, SYSDATETIME());
			COMMIT TRANSACTION LOG
			SET @RETURN_VALUE = -1;
			SELECT @RETURN_VALUE AS 'RETURN_VALUE';
			BREAK;
		END CATCH
	END

	IF (@BatchItemSize = 0) BEGIN
		BEGIN TRANSACTION LOG
			INSERT INTO claims_archive.process_log VALUES('Insert Into [claims_archive].[claim_item]', 'ARCHIVE_ITEM_LOAD', 'SUCCESS', @TotalItemSize, NULL, NULL, @MAX_TIME, SYSDATETIME());
		COMMIT TRANSACTION LOG
	END
	ELSE BEGIN
		BEGIN TRANSACTION LOG
			INSERT INTO claims_archive.process_log VALUES('Insert Into [claims_archive].[claim_item]', 'ARCHIVE_ITEM_LOAD', 'WARNING', @TotalItemSize, 'Initial Fetch Did Not Succeed', NULL, @MAX_TIME, SYSDATETIME());
		COMMIT TRANSACTION LOG
	END

	SET @errorMessage = NULL;
	CLOSE ItemCursor;
	DEALLOCATE ItemCursor;
END



SELECT @RETURN_VALUE AS 'RETURN_VALUE';
----
END;
