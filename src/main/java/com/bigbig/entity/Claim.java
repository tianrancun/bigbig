package com.bigbig.entity;


import com.bigbig.metadata.ClaimTypeCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "claim", schema = "dbo")
@ToString(exclude = { "items", "claimShipment" })
@EqualsAndHashCode(exclude = { "items", "claimShipment" })
public class Claim implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "claim_id", nullable = false, columnDefinition = "integer")
    private Long claimId;

    @Column(name = "club_nbr", nullable = false, length = 5)
    private Integer clubNbr;

    @Column(name = "claim_name", nullable = false, unique = true, length = 40)
    private String claimName;

    @Column(name = "claim_sequence_nbr", nullable = false, length = 4)
    private String claimSeqNbr;

    @Column(name = "claim_type_code", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ClaimTypeCode claimType;



    @Column(name = "store_notes_txt", length = 512)
    private String store_notes_txt;

    @Column(name = "return_auth_nbr", length = 24)
    private String returnAuthNbr;

    @Column(name = "claim_weight")
    private Double claimWeightQuantity;

    @Column(name = "ship_charge_amt")
    private Double shipChargeAmount;

    @Column(name = "last_modified_ts", nullable = false)
    private LocalDateTime lastModifiedTs;

    @Column(name = "last_modified_user", length = 24, nullable = false)
    private String lastModifiedUser;

    @Column(name = "claim_open_ts", nullable = false)
    private LocalDateTime claimOpenTs;

    @Column(name = "created_user", nullable = false, length = 24)
    private String createUserid;



    @OneToMany(mappedBy = "claim", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<ClaimItem> items;

    @Column(name = "vendor_name")
    private String vendorName;

    @Column(name = "vendor_nbr", columnDefinition = "integer")
    private Integer vendorNbr;

    @Column(name = "vendor_dept_nbr", columnDefinition = "integer")
    private Integer vendorDeptNbr;

    @Column(name = "hazmat_id", columnDefinition = "integer")
    private Long hazmatId;

    @Column(name = "tracking_nbr", length = 256)
    private String trackingNbr;


    @Column(name = "box_audited")
    private Boolean boxAudited;

    @Column(name = "box_audited_user", length = 24)
    private String boxAuditedUser;

    @Column(name = "box_audited_ts")
    private LocalDateTime boxAuditedTs;

    @Column(name = "recall_id", columnDefinition = "integer")
    private Integer recallId;

    @Column(name = "recall_end_date")
    private LocalDate recallEndDate;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "claim_shipment_id", nullable = false)
    private ClaimShipment claimShipment;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "pallet_id")
    private Pallet pallet;

    @JsonIgnore
    @Column(name = "pallet_id", updatable = false, insertable = false, columnDefinition = "integer")
    private Long palletId;

    public Claim() {
    }

//    @Transient
//    public Boolean isExpired(Integer threshold) {
//        Long daysOpen = DAYS.between(this.getClaimOpenTs(), LocalDateTime.now());
//        return daysOpen > threshold;
//    }

    @Transient
    public void setClaimCodes(ClaimTypeCode claimType) {
        this.claimType = claimType;
    }

    @Transient
    public String getStoreTxt() {
        return (ClaimTypeCode.SUPP == claimType && StringUtils.isNotBlank(store_notes_txt)) ? store_notes_txt : "";
    }

    // deprecated as new box audit required eligibility is available in claims
    // business manager
    @Deprecated
    @Transient
    public boolean isBoxAuditRequired() {
        if (!boxAudited) {
            if (ClaimTypeCode.RCTR == claimType || ClaimTypeCode.JRCTR == claimType) {
                return true;
            }

            if (ClaimTypeCode.SUPP == claimType && null != hazmatId) {
                return true;
            }
        }
        return false;
    }



}


