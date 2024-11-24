
package com.example.otp.service;

import com.example.otp.model.Otp;
import com.example.otp.repository.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private OtpRepository otpRepository;

    public String generateOtp(String userIdentifier) {
        String otpCode = String.valueOf(new Random().nextInt(900000) + 100000); // 6-digit OTP
        Otp otp = new Otp();
        otp.setUserIdentifier(userIdentifier);
        otp.setOtpCode(otpCode);
        otp.setExpiryTime(LocalDateTime.now().plusMinutes(5)); // Expires in 5 mins
        otp.setValid(true);
        otpRepository.save(otp);

        return otpCode; // Send this via email/SMS in real-world apps
    }

    public boolean validateOtp(String userIdentifier, String otpCode) {
        Optional<Otp> otp = otpRepository.findByUserIdentifierAndOtpCodeAndIsValid(userIdentifier, otpCode, true);
        if (otp.isPresent() && otp.get().getExpiryTime().isAfter(LocalDateTime.now())) {
            otp.get().setValid(false); // Mark OTP as used
            otpRepository.save(otp.get());
            return true;
        }
        return false;
    }
}
