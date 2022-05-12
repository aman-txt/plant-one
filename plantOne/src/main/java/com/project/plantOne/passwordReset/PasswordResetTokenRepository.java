package com.project.plantOne.passwordReset;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    PasswordResetToken findByToken(String token);

    void deleteByToken(String token);

    void deleteByExpiryDateLessThan(Date now);

//    @Modifying
//    @Query("delete from PasswordResetToken t where t.expiryDate <= ?1")
//    void deleteAllExpiredSince(Date now);
}
