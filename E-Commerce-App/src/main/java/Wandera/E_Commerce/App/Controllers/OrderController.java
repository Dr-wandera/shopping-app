package Wandera.E_Commerce.App.Controllers;

import Wandera.E_Commerce.App.Dtos.OrderRequest;
import Wandera.E_Commerce.App.Dtos.OrderResponse;
import Wandera.E_Commerce.App.Services.ServiceImpl.OrderEntityServiceImplementation;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {
    private final OrderEntityServiceImplementation orderEntityServiceImplementation;

    @PostMapping("/place")
    public OrderResponse placeOrder(@RequestBody OrderRequest orderRequest) throws MessagingException, IOException {
        return orderEntityServiceImplementation.placeOrder(orderRequest);
    }
}
