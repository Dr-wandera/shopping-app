package Wandera.E_Commerce.App.Entities;

import Wandera.E_Commerce.App.Enum.PaymentMethod;
import Wandera.E_Commerce.App.Enum.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String orderNumber;
    private int quantity;

    @CreationTimestamp
    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @Column(name = "total_amount")
    private int totalAmount;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(name = "paymentMethod")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<OrderItem> items = new LinkedList<>();
    private String checkoutRequestId;

    private String mpesaReceiptNumber;

    private LocalDateTime paidAt;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity customer;


    @PrePersist
    protected void onCreate() {
        generateOrderNumber();
    }

    public void generateOrderNumber() {
        this.orderNumber = "ORD" + System.currentTimeMillis();
        this.createdAt = LocalDateTime.now();
    }
}
