package Wandera.E_Commerce.App.Repositories;

import Wandera.E_Commerce.App.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity,Long> {
    @Query("SELECT u FROM UserEntity u " +
            "LEFT JOIN FETCH u.cart " +
            "LEFT JOIN FETCH u.sellerProfile " +
            "WHERE u.email = :email")
    Optional<UserEntity> findByEmail(String email);
}
