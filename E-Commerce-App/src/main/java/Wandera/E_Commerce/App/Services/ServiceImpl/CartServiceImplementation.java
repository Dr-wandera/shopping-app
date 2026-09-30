package Wandera.E_Commerce.App.Services.ServiceImpl;

import Wandera.E_Commerce.App.Dtos.CartItemResponse;
import Wandera.E_Commerce.App.Dtos.CartRequest;
import Wandera.E_Commerce.App.Dtos.CartResponse;
import Wandera.E_Commerce.App.Enum.Role;
import Wandera.E_Commerce.App.Entities.*;
import Wandera.E_Commerce.App.Exceptions.BadRequestException;
import Wandera.E_Commerce.App.Exceptions.ResourceNotFoundException;
import Wandera.E_Commerce.App.Exceptions.UnauthorizedException;
import Wandera.E_Commerce.App.Repositories.CartItemRepository;
import Wandera.E_Commerce.App.Repositories.CartRepository;
import Wandera.E_Commerce.App.Repositories.ProductRepository;
import Wandera.E_Commerce.App.Services.Interfaces.CartInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImplementation implements CartInterface {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserEntityImplementation userEntityImplementation;
    private final ProductRepository productRepository;

    @Override
    public CartResponse addToCart(CartRequest request) {

            UserEntity user =userEntityImplementation .getLoggedInUser();

            if (user==null) {
                throw new UnauthorizedException("You are not logged in. Please login again.");

            }

        if (user.getRole() == Role.SELLER) {
            throw new UnauthorizedException("Sellers cannot place orders or have a cart");
        }

            Cart cart = user.getCart();
            if (cart == null) {
                cart = Cart.builder()
                        .totalAmount(0.0)
                        .user(user)
                        .items(new LinkedHashSet<>())
                        .build();
                cartRepository.save(cart);
                user.setCart(cart);
            }

            Product product = productRepository.findByProductCode(request.getProductCode())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            CartItem cartItem = cart.getItems() != null ?
                    cart.getItems().stream()
                            .filter(i -> i.getProduct().getProductId().equals(product.getProductId()))
                            .findFirst()
                            .orElse(null) : null;

            if (cartItem != null) {
                cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
                cartItem.calculateSubTotal();
                cartItemRepository.save(cartItem);
            } else {
                cartItem = CartItem.builder()
                        .cart(cart)
                        .product(product)
                        .quantity(request.getQuantity())
                        .subTotal(product.getProductPrice() * request.getQuantity())
                        .build();
                cartItemRepository.save(cartItem);
                cart.getItems().add(cartItem);
                System.out.println("PRODUCT: " + cartItem.getProduct().getProductId());

            }

            recalculateCartTotal(cart);
            cartRepository.save(cart);
            return mapToDTO(cart);
        }

    @Override
    public void deleteCartItem(Long id) {
        UserEntity user = userEntityImplementation.getLoggedInUser();
        Cart cart = user.getCart();

        if (cart == null || cart.getItems().isEmpty()) {
            throw new BadRequestException("Cart is empty");
        }

        // Find the item in the cart
        CartItem itemToRemove = cart.getItems().stream()
                .filter(item -> item.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("CartItem not found"));


        cart.getItems().remove(itemToRemove);


        cartItemRepository.delete(itemToRemove);


        double newTotal = cart.getItems().stream()
                .mapToDouble(CartItem::getSubTotal)
                .sum();

        cart.setTotalAmount(newTotal);

        cartRepository.save(cart);
    }
    @Override
    public List<CartResponse> getCartItems(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return cartRepository.findAll(pageable)
                .stream()
                .map(this::mapToDTO)
                .toList();

    }


    private void recalculateCartTotal(Cart cart) {
            double total = cart.getItems().stream()
                    .mapToDouble(CartItem::getSubTotal)
                    .sum();
            cart.setTotalAmount(total);
        }

        private CartResponse mapToDTO(Cart cart) {
            List<CartItemResponse> items = cart.getItems().stream()
                    .map(i -> CartItemResponse.builder()
                            .id(i.getId())
                            .productId(i.getProduct().getProductId())
                            .productName(i.getProduct().getProductName())
                            .quantity(i.getQuantity())
                            .subTotal(i.getSubTotal())
                            .build())
                    .collect(Collectors.toList());

            return CartResponse.builder()
                    .id(cart.getId())
                    .totalAmount(cart.getTotalAmount())
                    .items(items)
                    .build();
        }

}
