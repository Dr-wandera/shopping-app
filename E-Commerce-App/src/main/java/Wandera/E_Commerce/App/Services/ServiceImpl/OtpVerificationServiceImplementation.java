package Wandera.E_Commerce.App.Services.ServiceImpl;

import Wandera.E_Commerce.App.EmailConfig.EmailService;
import Wandera.E_Commerce.App.Entities.OtpVerification;
import Wandera.E_Commerce.App.Entities.UserEntity;
import Wandera.E_Commerce.App.Repositories.OtpVerificationRepository;
import Wandera.E_Commerce.App.Repositories.UserEntityRepository;
import Wandera.E_Commerce.App.Services.Interfaces.OtpVerificationServiceInterface;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OtpVerificationServiceImplementation implements OtpVerificationServiceInterface {

     private final OtpVerificationRepository otpVerificationRepository;
     private final EmailService emailService;
     private final UserEntityRepository userEntityRepository;

    List<Integer> saveOtp=new ArrayList<>();

    // Generate and send OTP for registered user to verify the account
    public void verifyEmailAdress(UserEntity user) throws MessagingException, IOException {

        String otp = String.valueOf((int)(Math.random() * 900000) + 100000);

        OtpVerification token = new OtpVerification();
        token.setOtpCode(otp);
        token.setUser(user);
        token.setExpiryTime(LocalDateTime.now().plusMinutes(10));

        saveOtp.add(otp.hashCode());
        otpVerificationRepository.save(token);

        // Prepare template variables
        Map<String, String> variables = new HashMap<>();
        variables.put("otp", (otp));
        variables.put("username", user.getFirstName() );

        // This sends  OTP email to registered user
        emailService.sendOtpRegisterVerification(
                user.getEmail(),
                "Your Account Verification OTP",
                "OtpVerification.html",
                variables
        );
    }

      //verify user account from false to true
    public void verifyEmailByAOtp(Long otpCode) {

        // 1. Find otp if not in the database or expired throw invalid otp
        OtpVerification otp = otpVerificationRepository.findByOtpCode(otpCode)
                .orElseThrow(() -> new RuntimeException("Invalid OTP check and try again"));


        // Checks the  expiration time
        if (otp.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }


        UserEntity user = otp.getUser();

        // 4. Verify user
        user.setVerified(true);
        userEntityRepository.save(user);

        saveOtp.remove(otp.hashCode());
        otpVerificationRepository.delete(otp);
    }


    //this method responsible for resending the otp if the one send has expired
    //enter the otp that has expired to find the user (owner of the otp) to create another one for verification
    @Override
    public void resendCodeToken(Long otpCode) throws MessagingException, IOException {
        OtpVerification otpEntity = otpVerificationRepository.findByOtpCode(otpCode)
                .orElseThrow(() -> new RuntimeException("Invalid OTP. Check and try again"));

        UserEntity user = otpEntity.getUser();

        if (user.isVerified()) {
            throw new RuntimeException("Account already verified");
        }

        // delete old otp
        otpVerificationRepository.delete(otpEntity);

        // generate new otp
        String otp = String.format("%06d", new Random().nextInt(999999));

        OtpVerification newOtp = new OtpVerification();
        newOtp.setOtpCode(otp);
        newOtp.setUser(user);
        newOtp.setExpiryTime(LocalDateTime.now().plusMinutes(5));

        saveOtp.add(otp.hashCode());
        otpVerificationRepository.save(newOtp);

        // This sends  OTP email to registered user
        // Prepare template variables
        Map<String, String> variables = new HashMap<>();
        variables.put("username", user.getFirstName() );
        variables.put("otp", (otp));

        emailService.resendOtpEmail(
                user.getEmail(),
                "Your Password Reset Code",
                "OtpVerification.html",
                variables
        );

    }
}
