package Wandera.E_Commerce.App.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long productId;
    private String productCode;
    private String productName;
    private String productDescription;
    private Double productPrice;
    private String categoryName;
    private String backgroundColor;
    private String productImageUrl;
    private Integer productStock;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


    @ManyToOne
    @JoinColumn(name = "seller_id")
    private SellerProfile seller;

}
