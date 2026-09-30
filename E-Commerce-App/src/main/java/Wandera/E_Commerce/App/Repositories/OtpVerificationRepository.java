package Wandera.E_Commerce.App.Repositories;

import Wandera.E_Commerce.App.Entities.OtpVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface OtpVerificationRepository extends JpaRepository<OtpVerification, Long> {
    Optional<OtpVerification> findByOtpCode(Long otp);
}
