package Wandera.E_Commerce.App.Services.Interfaces;

import Wandera.E_Commerce.App.Entities.UserEntity;
import jakarta.mail.MessagingException;

import java.io.IOException;

public interface OtpVerificationServiceInterface {
    void verifyEmailAdress(UserEntity user) throws MessagingException, IOException;
    void verifyEmailByAOtp(Long otpCode);

    void resendCodeToken(Long otpCode) throws MessagingException, IOException;
}
