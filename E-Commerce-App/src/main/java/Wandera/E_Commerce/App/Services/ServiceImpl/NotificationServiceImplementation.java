package Wandera.E_Commerce.App.Services.ServiceImpl;

import Wandera.E_Commerce.App.EmailConfig.EmailService;
import Wandera.E_Commerce.App.Entities.*;
import Wandera.E_Commerce.App.Repositories.NotificationRepository;
import Wandera.E_Commerce.App.Services.Interfaces.NotificationInterface;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationServiceImplementation implements NotificationInterface {
    private final EmailService emailService;
    private final NotificationRepository notificationRepository;

    //this notifies the seller for the sell he made
    @Override
    public void notifySellerOnOrder(SellerProfile seller, OrderItem orderItem, UserEntity customer) throws MessagingException, IOException {

        if (seller == null) return;

        Map<String, String> variables = new HashMap<>();
        variables.put("Product", orderItem.getProduct().getProductName());
        variables.put("Quantity", orderItem.getQuantity() + "");
        variables.put("SubTotal", orderItem.getSubTotal() + "");
        variables.put("Customer Email", customer.getEmail());
        variables.put("Phone", customer.getPhoneNumber());
        variables.put("Location", customer.getCountry());

        emailService.sendEmailToSeller(
                seller.getEmail(),
                "You made a sale!",
                "Seller_notification.html",
                variables
        );

        // ✔ Save notification
        Notification notification = Notification.builder()
                .user(seller.getUser())
                .message("You have a new order for: " +
                        orderItem.getProduct().getProductName())
                .createdAt(LocalDateTime.now())
                .build();

        notificationRepository.save(notification);
    }

    //  BUYER NOTIFICATION
    @Override
    public void notifyBuyerOnOrderPlaced(UserEntity buyer, OrderEntity order) throws MessagingException, IOException {


            Map<String, String> variables = new HashMap<>();
            variables.put("firstName", buyer.getFirstName());
            variables.put("orderNumber", order.getOrderNumber());
            variables.put("totalAmount", String.valueOf(order.getTotalAmount()));
            variables.put("orderDate", order.getCreatedAt().toString());


            //  Send email
            emailService.sendEmailToBuyer(
                    buyer.getEmail(),
                    "Order Confirmation",
                    "order-confirmation",
                    variables
            );


            // 🛎 Save notification
            Notification notification = Notification.builder()
                    .user(buyer)
                    .message("Your order " + order.getOrderNumber() + " was placed successfully.")
                    .createdAt(LocalDateTime.now())
                    .build();

            notificationRepository.save(notification);
        }

    }



