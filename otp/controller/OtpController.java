
package com.example.otp.controller;

import com.example.otp.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @PostMapping("/generate")
    public ResponseEntity<String> generateOtp(@RequestParam String userIdentifier) {
        String otp = otpService.generateOtp(userIdentifier);
        return ResponseEntity.ok("OTP sent to " + userIdentifier + ": " + otp);
    }

    @PostMapping("/validate")
    public ResponseEntity<String> validateOtp(@RequestParam String userIdentifier, @RequestParam String otpCode) {
        boolean isValid = otpService.validateOtp(userIdentifier, otpCode);
        if (isValid) {
            return ResponseEntity.ok("OTP verified successfully!");
        } else {
            return ResponseEntity.status(401).body("Invalid or expired OTP");
        }
    }
}
