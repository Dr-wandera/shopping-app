package Wandera.E_Commerce.App.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryRequest {
    private Long sellerId;
    private Integer productStock;
    private String productCode;
}
