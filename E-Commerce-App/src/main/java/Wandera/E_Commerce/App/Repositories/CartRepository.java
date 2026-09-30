package Wandera.E_Commerce.App.Repositories;

import Wandera.E_Commerce.App.Entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart findById(long id);
}
