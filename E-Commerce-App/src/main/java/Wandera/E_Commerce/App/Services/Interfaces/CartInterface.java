package Wandera.E_Commerce.App.Services.Interfaces;

import Wandera.E_Commerce.App.Dtos.CartRequest;
import Wandera.E_Commerce.App.Dtos.CartResponse;

import java.util.List;

public interface CartInterface {
    CartResponse addToCart(CartRequest request);

    void deleteCartItem(Long id);

    List<CartResponse> getCartItems(int page, int size);
}
