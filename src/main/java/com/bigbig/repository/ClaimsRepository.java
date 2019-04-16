package com.bigbig.repository;//package com.bigbig.repository;

import com.bigbig.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimsRepository extends JpaRepository<Claim, Long> {
}