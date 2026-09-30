package Wandera.E_Commerce.App.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private Long id;
    private String productName;
    private String productDescription;
    private Double productPrice;
    private String categoryName;
    private String backgroundColor;
    private String productImageUrl;
    private Integer productStock;
}
