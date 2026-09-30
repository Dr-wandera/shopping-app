package Wandera.E_Commerce.App.Repositories;

import Wandera.E_Commerce.App.Entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {
}
