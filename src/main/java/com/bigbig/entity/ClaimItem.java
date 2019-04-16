package com.bigbig.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "claim_item", schema = "dbo")
@EqualsAndHashCode(exclude = "claim")
public class ClaimItem
    implements Serializable {

    private static final long serialVersionUID = -759198559768887950L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "claim_item_id", columnDefinition = "integer", nullable = false)
    private Long claimItemId;

    @Column(name = "item_nbr", columnDefinition = "integer", nullable = false)
    private Long itemNbr;

    @Column(name = "upc_nbr", nullable = false)
    private String upcNbr;

    @Column(name = "gtin_nbr", nullable = false)
    private String gtin;

    @Column(name = "cat_id", columnDefinition = "integer")
    private Integer catId;

    @Column(name = "item_desc")
    private String itemDescription;

    @Column(name = "item_desc2")
    private String itemDescription2;

    @Column(name = "vendor_name", nullable = false)
    private String vendorName;

    @Column(name = "vendor_nbr", columnDefinition = "integer", nullable = false)
    private Integer vendorNbr;

    @Column(name = "vendor_seq_nbr", columnDefinition = "smallint", nullable = false)
    private Integer vendorSeqNbr;

    @Column(name = "vendor_dept_nbr", columnDefinition = "smallint", nullable = false)
    private Integer vendorDeptNbr;

    @Column(name = "item_qty", columnDefinition = "smallint", nullable = false)
    private int itemQty;

    @Column(name = "virt_qty", columnDefinition = "smallint", nullable = false)
    private int virtQty;

    @Column(name = "item_cost_amt", nullable = false)
    private BigDecimal itemCostAmount;

    @Column(name = "item_sell_amt", nullable = false)
    private BigDecimal itemSellAmount;

    @Column(name = "item_retail_amt", nullable = false)
    private BigDecimal itemRetailAmount;

    @Column(name = "vendor_pack_qty", columnDefinition = "smallint")
    private Integer vendorPackQuantity;

    @Column(name = "vendor_pack_amt")
    private BigDecimal vendorPackAmount;

    @Column(name = "vendor_credit_amt")
    private BigDecimal vendorCreditAmount;

    @Column(name = "handling_charge_amt")
    private BigDecimal handlingChargeAmount;

    @Column(name = "disposal_charge_amt")
    private BigDecimal disposalChargeAmount;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "claim_id", nullable = false)
    private Claim claim;

    @JsonIgnore
    @Column(name = "claim_id", updatable = false, insertable = false, columnDefinition = "integer")
    private Long claimId;

    @Column(name = "bucket_id", columnDefinition = "smallint")
    private Integer bucketId;

    @Column(name = "bucket_description")
    private String bucketDescription;

    @Column(name = "hazmat_id", columnDefinition = "integer")
    private Long hazmatId;

    @Column(name = "stock_number")
    private String stockNumber;

    @Column(name = "hazwaste", columnDefinition = "smallint")
    private Integer hazwaste;

    @Column(name = "bucket_color")
    private String bucketColor;


    @Column(name = "damage_reason")
    private String damageReason;

    @Column(name = "created_user", length = 24, nullable = false)
    private String createdUser;

    @Column(name = "created_ts", nullable = false)
    private LocalDateTime createdTs;

    @Column(name = "last_modified_user", length = 24, nullable = false)
    private String lastModifiedUser;

    @Column(name = "last_modified_ts", nullable = false)
    private LocalDateTime lastModifiedTs;

    @Column(name = "cm_rules", length = 2048)
    private String cmRules;

    @Column(name = "dot_haz_class_code", length = 12)
    private String dotHazClassCode;

    @Column(name = "dot_haz_class_code_desc", length = 48)
    private String dotHazClassCodeDesc;

    @Column(name = "dot_reg_code", length = 24)
    private String dotRegCode;

    @Column(name = "dot_hazmat_nbr", length = 12)
    private String dotHazmatNbr;

    @Column(name = "shipping_name", length = 254)
    private String shippingName;


    @Column(name = "dot_packing_code", length = 12)
    private String dotPackingCode;

    @Column(name = "dot_exemption_nbr", length = 24)
    private String dotExemptionNbr;

    @Column(name = "flash_point_amt", length = 12)
    private String flashPointAmt;

    @Column(name = "flash_point_uom", length = 12)
    private String flashPointUOM;

    @Column(name = "marine_pollute_ind")
    private Boolean marinePolluteInd;

    

}

