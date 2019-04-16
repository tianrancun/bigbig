package com.bigbig.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Table(name = "claim_shipment", schema = "dbo")
@Data
@ToString
@EqualsAndHashCode
public class ClaimShipment
    implements Serializable {



    private static final long serialVersionUID = -7776228312947568836L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "claim_shipment_id", nullable = false, columnDefinition = "integer")
    private Long claimShipmentId;

    @Column(name = "shipper_name", nullable = false)
    private String shipperName;

    @Column(name = "create_ts", nullable = false)
    private LocalDateTime createTs;

    @Column(name = "attention_name")
    private String attentionName;

    @Column(name = "address_line1")
    private String addressLine1;

    @Column(name = "address_line2")
    private String addressLine2;

    @Column(name = "state_prov_cd")
    private String stateProvCd;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "ship_to_company")
    private String shipToCompany;

    @JsonIgnore
    @OneToOne(mappedBy = "claimShipment")
    private Claim claim;

    public ClaimShipment() {
    }

    public ClaimShipment(LocalDateTime createTs) {
        this.createTs = createTs;
    }
}

