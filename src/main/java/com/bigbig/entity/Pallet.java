package com.bigbig.entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;


@Entity
@Table(name = "claim_pallet", schema = "dbo")
@Data
@ToString(exclude = { "claims" })
@EqualsAndHashCode(exclude = { "claims" })
public class Pallet
    implements
    Serializable {


    private static final long serialVersionUID = -3132377819072908624L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pallet_id", nullable = false, columnDefinition = "integer")
    private Long palletId;

    @Column(name = "created_user", nullable = false)
    private String createdUser;

    @Column(name = "created_ts", nullable = false)
    private LocalDateTime createdTs;

    @Column(name = "last_modified_user", nullable = false)
    private String lastModifiedUser;

    @Column(name = "last_modified_ts", nullable = false)
    private LocalDateTime lastModifiedTs;

    @Column(name = "pickedup_user")
    private String pickedupUser;

    @Column(name = "pickedup_ts")
    private LocalDateTime pickedupTs;


    @Column(name = "club_nbr", nullable = false)
    private Integer clubNbr;

    @OneToMany(mappedBy = "pallet", fetch = FetchType.LAZY)
    private Set<Claim> claims;

}

