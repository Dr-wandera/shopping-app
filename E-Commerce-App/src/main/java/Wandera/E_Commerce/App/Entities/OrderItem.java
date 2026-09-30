package Wandera.E_Commerce.App.Entities;

import jakarta.persistence.*;
import lombok.*;



@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity")
    private int quantity;
    private double subTotal;

    @ManyToOne
    private OrderEntity order;

    @ManyToOne
    private Product product;

}
