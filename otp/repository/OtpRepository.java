
package com.example.otp.repository;

import com.example.otp.model.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {
    Optional<Otp> findByUserIdentifierAndOtpCodeAndIsValid(String userIdentifier, String otpCode, boolean isValid);
}
