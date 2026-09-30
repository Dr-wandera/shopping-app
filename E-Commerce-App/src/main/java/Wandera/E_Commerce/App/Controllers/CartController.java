package Wandera.E_Commerce.App.Controllers;

import Wandera.E_Commerce.App.Dtos.CartRequest;
import Wandera.E_Commerce.App.Dtos.CartResponse;
import Wandera.E_Commerce.App.Services.ServiceImpl.CartServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartServiceImplementation cartServiceImplementation;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public CartResponse addToCart(@RequestBody CartRequest request) {
        return cartServiceImplementation.addToCart(request);
    }

    @DeleteMapping("/delete_item/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteCartItem(@PathVariable Long id) {
        cartServiceImplementation.deleteCartItem(id);
        return ResponseEntity.ok("Cart item deleted successfully");
    }
    @GetMapping("/items")
    @ResponseStatus(HttpStatus.OK)
    public List<CartResponse> getCartItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return cartServiceImplementation.getCartItems(page,size);
    }

}
