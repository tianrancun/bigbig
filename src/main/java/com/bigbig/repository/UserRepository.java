package com.bigbig.repository;

import com.bigbig.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
//@Repository
//public interface UserRepository extends CrudRepository<User, Integer> {
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
     Optional<User> findByClaimId(Integer claimid);

//    @Query("select c from Claim c join fetch c.items i where c.claimId = ?1 and c.clubNbr = ?2 and c.state != 'DELETED' and i.isBackfeedFail = 0 and i.isDeletedOnCorr = 0")
//    public Optional<Claim> findByClaimIdByClub(Long claimid, Integer clubNbr);
}