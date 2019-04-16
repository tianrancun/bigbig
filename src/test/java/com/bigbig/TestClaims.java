package com.bigbig;


import com.bigbig.entity.Claim;
import com.bigbig.entity.ClaimShipment;
import com.bigbig.repository.ClaimsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = App.class)
@SpringBootTest
public class TestClaims {
    @Autowired
    private ClaimsRepository claimsRepository;
    @Test
    public void saveClaims() throws Exception {
        Claim claim = new Claim();
        claim.setClubNbr(123);

        ClaimShipment claimShipment = new ClaimShipment();
        claimShipment.setAddressLine1("address1");
        claimShipment.setCreateTs(LocalDateTime.now());
        claimShipment.setShipperName("Mr.zh");
        claim.setClaimShipment(claimShipment);
        claimsRepository.save(claim);

    }



}