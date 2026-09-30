package Wandera.E_Commerce.App.Services.Interfaces;

import Wandera.E_Commerce.App.Entities.OrderEntity;
import Wandera.E_Commerce.App.Entities.OrderItem;
import Wandera.E_Commerce.App.Entities.SellerProfile;
import Wandera.E_Commerce.App.Entities.UserEntity;
import jakarta.mail.MessagingException;

import java.io.IOException;

public interface NotificationInterface {

    void notifySellerOnOrder(
            SellerProfile seller,
            OrderItem orderItem,
            UserEntity customer
    ) throws MessagingException, IOException;

    void notifyBuyerOnOrderPlaced(UserEntity buyer, OrderEntity order) throws MessagingException, IOException;

}
