package Wandera.E_Commerce.App.Entities;

import jakarta.persistence.*;
import lombok.*;



@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int quantity;
    private double subTotal;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    private Product product;

    public void calculateSubTotal() {
        this.subTotal = product.getProductPrice() * quantity;
    }

}
