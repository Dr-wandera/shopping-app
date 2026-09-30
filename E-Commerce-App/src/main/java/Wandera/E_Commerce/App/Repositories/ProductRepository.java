package Wandera.E_Commerce.App.Repositories;

import Wandera.E_Commerce.App.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findAllByProductNameIgnoreCase(String productName);
    Optional<Product> findByProductId(Long productId);

    boolean existsByProductCode(String productId);

    Optional<Product> findByProductCode(String productCode);
}
