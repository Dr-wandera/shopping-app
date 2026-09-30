package Wandera.E_Commerce.App.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestockRequest {
    private String productCode;
    private Integer productStock;
}
