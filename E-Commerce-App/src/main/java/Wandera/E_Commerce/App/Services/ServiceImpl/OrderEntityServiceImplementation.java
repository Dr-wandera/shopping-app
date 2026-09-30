package Wandera.E_Commerce.App.Services.ServiceImpl;

import Wandera.E_Commerce.App.Dtos.OrderItemResponse;
import Wandera.E_Commerce.App.Dtos.OrderRequest;
import Wandera.E_Commerce.App.Dtos.OrderResponse;
import Wandera.E_Commerce.App.Enum.PaymentStatus;
import Wandera.E_Commerce.App.Entities.*;
import Wandera.E_Commerce.App.Repositories.CartRepository;
import Wandera.E_Commerce.App.Repositories.OrderEntityRepository;
import Wandera.E_Commerce.App.Repositories.ProductRepository;
import Wandera.E_Commerce.App.Services.Interfaces.OrderEntityInterface;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderEntityServiceImplementation implements OrderEntityInterface {

    private final UserEntityImplementation userService;
    private final OrderEntityRepository orderRepository;
    private final CartRepository cartRepository;
    private final NotificationServiceImplementation notificationService;
    private final ProductRepository productRepository;



    @Override
    @Transactional
    public OrderResponse placeOrder(OrderRequest orderRequest)
            throws MessagingException, IOException {

        UserEntity user = userService.getLoggedInUser();
        Cart cart = user.getCart();

        if (cart == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // Create order
        OrderEntity order = OrderEntity.builder()
                .customer(user)
                .paymentMethod(orderRequest.getPaymentMethod())
                .createdAt(LocalDateTime.now())
                .quantity(cart.getQuantity())
                .status(PaymentStatus.PENDING)
                .build();

        order.generateOrderNumber();

        // Convert CartItems → OrderItems
        List<OrderItem> orderItems = cart.getItems().stream()
                .map(cartItem -> {

                    // Check stock
                    if (cartItem.getProduct().getProductStock()
                            < cartItem.getQuantity()) {

                        throw new RuntimeException(
                                "Insufficient stock for product: "
                                        + cartItem.getProduct().getProductName()
                        );
                    }

                    return OrderItem.builder()
                            .order(order)
                            .product(cartItem.getProduct())
                            .quantity(cartItem.getQuantity())
                            .subTotal(cartItem.getSubTotal())
                            .build();

                })
                .collect(Collectors.toList());

        order.setItems(orderItems);

        int totalAmount = (int) orderItems.stream()
                .mapToDouble(OrderItem::getSubTotal)
                .sum();

        order.setTotalAmount(totalAmount);

        if ("CASH".equalsIgnoreCase(
                String.valueOf(orderRequest.getPaymentMethod()))) {

            order.setStatus(PaymentStatus.PAID);

            // Save order first
            orderRepository.save(order);

            //reduce stock
            reduceStock(orderItems);

            // Notify sellers
            notifySellers(orderItems, user);

            // Notify buyer
            notificationService.notifyBuyerOnOrderPlaced(user, order);

            // Clear cart
            clearCart(cart);

        }

        //other payment save the payment status to be pending
        else {

            order.setStatus(PaymentStatus.PENDING);

            orderRepository.save(order);
        }

        // Build response
        List<OrderItemResponse> itemResponses =
                orderItems.stream()
                        .map(item -> OrderItemResponse.builder()
                                .productId(
                                        item.getProduct().getProductId())
                                .productName(
                                        item.getProduct().getProductName())
                                .quantity(item.getQuantity())
                                .subTotal(item.getSubTotal())
                                .build())
                        .collect(Collectors.toList());

        return OrderResponse.builder()
                .id(order.getId())
                .totalAmount(order.getTotalAmount())
                .createdAt(order.getCreatedAt())
                .orderItems(itemResponses)
                .build();
    }

    //clears chart after order being been placed
    private void clearCart(Cart cart) {

        cart.getItems().clear();
        cart.setTotalAmount(0);

        cartRepository.save(cart);
    }

    // notify seller of his product ordered
    private void notifySellers(
            List<OrderItem> orderItems,
            UserEntity user) throws MessagingException, IOException {

        for (OrderItem item : orderItems) {

            notificationService.notifySellerOnOrder(
                    item.getProduct().getSeller(),
                    item,
                    user
            );
        }
    }

    //reduce product stock after purchase
    private void reduceStock(List<OrderItem> orderItems) {
        for (OrderItem item : orderItems) {

            Product product = item.getProduct();

            if (product.getProductStock() < item.getQuantity()) {
                throw new RuntimeException(
                        "Insufficient stock for product: "
                                + product.getProductName()
                );
            }

            product.setProductStock(
                    product.getProductStock() - item.getQuantity()
            );

            productRepository.save(product);
        }
    }

    @Override
    public List<OrderResponse> getAllOrder(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        List<OrderEntity> orders = orderRepository.findByOrderByCreatedAtDesc(pageable);

        return orders.stream().map(order -> {

            List<OrderItemResponse> itemResponses = order.getItems().stream()
                    .map(item -> OrderItemResponse.builder()
                            .productId(item.getProduct().getProductId())
                            .productName(item.getProduct().getProductName())
                            .quantity(item.getQuantity())
                            .subTotal(item.getSubTotal())
                            .build())
                    .collect(Collectors.toList());

            return OrderResponse.builder()
                    .id(order.getId())
                    .totalAmount(order.getTotalAmount())
                    .createdAt(order.getCreatedAt())
                    .orderItems(itemResponses)
                    .build();

        })
                .collect(Collectors
                        .toList());
    }

    @Override
    public OrderResponse getByOrderId(String orderNumber) {

        OrderEntity order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(item -> OrderItemResponse.builder()
                        .productId(item.getProduct().getProductId())
                        .productName(item.getProduct().getProductName())
                        .quantity(item.getQuantity())
                        .subTotal(item.getSubTotal() * item.getQuantity())
                        .build())
                .toList();

        return OrderResponse.builder()
                .id(order.getId())
                .totalAmount(order.getTotalAmount())
                .createdAt(order.getCreatedAt())
                .orderItems(itemResponses)
                .build();
    }

}
