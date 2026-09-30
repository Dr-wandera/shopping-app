package Wandera.E_Commerce.App.Services.Interfaces;


import Wandera.E_Commerce.App.Dtos.OrderRequest;
import Wandera.E_Commerce.App.Dtos.OrderResponse;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.List;

public interface OrderEntityInterface {
    OrderResponse placeOrder(OrderRequest orderRequest) throws MessagingException, IOException;


    List<OrderResponse> getAllOrder(int page, int size);

    OrderResponse getByOrderId(String orderNumber);

}
