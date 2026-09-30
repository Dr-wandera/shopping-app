package Wandera.E_Commerce.App.Controllers;

import Wandera.E_Commerce.App.Dtos.OtpRequest;
import Wandera.E_Commerce.App.Dtos.ProfileRequest;
import Wandera.E_Commerce.App.Services.ServiceImpl.OtpVerificationServiceImplementation;
import Wandera.E_Commerce.App.Services.ServiceImpl.UserEntityImplementation;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserEntityImplementation userEntityImplementation;
    private final OtpVerificationServiceImplementation  otpVerificationServiceImplementation;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody ProfileRequest profileRequest) throws MessagingException, IOException {

         userEntityImplementation.registerUser(profileRequest);

        return ResponseEntity.ok(
                Map.of(
                        "message", "User registered successfully. Check your email for OTP verification.",
                        "email", profileRequest.getEmail()
                )
        );
    }
    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody OtpRequest request) {
        otpVerificationServiceImplementation.verifyEmailByAOtp(request.getOtpCode());
        return ResponseEntity.ok("Account verified successfully!");
    }

    @PostMapping("/resendCode")
    public ResponseEntity<?> resendCode(@RequestBody OtpRequest request) throws MessagingException, IOException {
        otpVerificationServiceImplementation.resendCodeToken(request.getOtpCode());
        return ResponseEntity.ok("Code sent. Check your email for OTP to activate your account.");
    }

}

