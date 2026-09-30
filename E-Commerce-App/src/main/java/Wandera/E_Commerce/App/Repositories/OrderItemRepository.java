package Wandera.E_Commerce.App.Repositories;

import Wandera.E_Commerce.App.Entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
