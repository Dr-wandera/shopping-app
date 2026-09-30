package Wandera.E_Commerce.App.Entities;

import Wandera.E_Commerce.App.Enum.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(
        name = "user_entity",
        indexes = {
                // This is the crucial part that
                //this creates index for email for faster look up for user logging in.Tells Postgres to create the fast O(log n) index
                @Index(name = "idx_users_email_lower", columnList = "lower(email)", unique = true)
        }
)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
   @NotBlank(message = "First name required")
    private String firstName;

    @NotBlank(message = "last name required")
    private String lastName;
    @NotBlank(message = "email cannot be blank")
    @Email
    @Column(unique = true)
    private String email;
    @NotBlank(message = "Password is required")

    @NotBlank(message = "Password is required")
    @Pattern(
            regexp = "$(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{6,}",
            message = "Password must be at least 6 characters, include uppercase, lowercase, digit, and special character"
    )
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
    @NotBlank(message = "Phone number cannot be blank")
    @Column(unique = true)
    private String phoneNumber;
    private String country;
    @CreationTimestamp
    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd ")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd ")
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Cart cart;



    @OneToOne(mappedBy = "user")
    private SellerProfile sellerProfile;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<OrderEntity> orders;

    private boolean verified = false;



}
