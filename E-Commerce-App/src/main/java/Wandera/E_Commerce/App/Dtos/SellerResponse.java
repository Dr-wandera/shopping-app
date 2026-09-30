package Wandera.E_Commerce.App.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellerResponse {
    private Long id;
    private String phoneNumber;
    private String idNumber;
    private String storeAddress;
    private String storeName;
    private String accountNumber;
}
