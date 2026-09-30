package Wandera.E_Commerce.App.Services.ServiceImpl;

import Wandera.E_Commerce.App.Dtos.ResetPasswordRequest;
import Wandera.E_Commerce.App.EmailConfig.EmailService;
import Wandera.E_Commerce.App.Dtos.ForgetPasswordRequest;
import Wandera.E_Commerce.App.Entities.PasswordResetToken;
import Wandera.E_Commerce.App.Entities.UserEntity;
import Wandera.E_Commerce.App.Exceptions.BadRequestException;
import Wandera.E_Commerce.App.Exceptions.ResourceNotFoundException;
import Wandera.E_Commerce.App.Repositories.PasswordResetTokenRepository;
import Wandera.E_Commerce.App.Repositories.UserEntityRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UserEntityRepository  userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    //this generates token
    private String generateNumericCode(int length) {

        //generate token to be sent for password reset
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    @Transactional
       public String createPasswordResetToken(ForgetPasswordRequest forgetPasswordRequest) throws MessagingException, IOException {

        UserEntity user = userRepository.findByEmail(forgetPasswordRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Email not found"));


        String token = generateNumericCode(6);

        PasswordResetToken reset = new PasswordResetToken();
        reset.setToken(token);
        reset.setUser(user);
        reset.setExpiration(LocalDateTime.now().plusMinutes(10));


        passwordResetTokenRepository.save(reset);

        // Prepare template variables
        Map<String, String> variables = new HashMap<>();
        variables.put("otpCode", token);
        variables.put("username", user.getFirstName() );
        variables.put("expiry", reset.getExpiration().toString());

        emailService.sendEmailResetToken(
                user.getEmail(),
                "Your Password Reset Code",
                "ResetToken.html",
                variables
        );


        return token;

    }

    //this method sets new user password
    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        PasswordResetToken reset = passwordResetTokenRepository.findByToken(resetPasswordRequest.getToken())
                .orElseThrow(() -> new BadRequestException("Invalid token"));

        if (reset.getExpiration().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Token has expired");
        }

        UserEntity user = reset.getUser();
        user.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));

        if (reset.getExpiration().isBefore(LocalDateTime.now())) {
            userRepository.save(user);
            passwordResetTokenRepository.delete(reset);
        }

        userRepository.save(user);
        passwordResetTokenRepository.delete(reset);

    }
}
