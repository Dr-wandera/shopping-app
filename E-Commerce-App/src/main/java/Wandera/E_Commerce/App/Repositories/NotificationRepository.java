package Wandera.E_Commerce.App.Repositories;

import Wandera.E_Commerce.App.Entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository  extends JpaRepository<Notification,Long> {
}
