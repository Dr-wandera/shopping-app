package Wandera.E_Commerce.App.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class SellerProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String sellerId;

    @Email
    @NotBlank(message = "Email required")
    private String email;
    @NotBlank(message ="Id number required" )
    private String idNumber;

    @NotBlank(message ="Phone number required" )
    private String phoneNumber;

    @NotBlank(message ="Store name required" )
    private String storeName;

    @NotBlank(message = "Account number required")
    private String AccountNumber;

    @NotBlank(message ="Store address required" )
    private String storeAddress;
    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private UserEntity user;
}
