package Wandera.E_Commerce.App.Repositories;

import Wandera.E_Commerce.App.Entities.PasswordResetToken;
import Wandera.E_Commerce.App.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, String> {
    Optional<PasswordResetToken> findByToken(String token);

    void deleteByUser(UserEntity user);
}
