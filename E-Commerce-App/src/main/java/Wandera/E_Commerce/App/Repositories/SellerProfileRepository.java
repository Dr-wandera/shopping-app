package Wandera.E_Commerce.App.Repositories;

import Wandera.E_Commerce.App.Entities.SellerProfile;
import Wandera.E_Commerce.App.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerProfileRepository extends JpaRepository<SellerProfile,Long> {
    Optional<SellerProfile> findByEmail(String email);

    Optional<SellerProfile> findById(Long id);
    Optional<SellerProfile> findByUser(UserEntity userEntity);

    Optional<SellerProfile> findBySellerId(Long sellerId);
}
