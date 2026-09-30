package Wandera.E_Commerce.App.Dtos;

import Wandera.E_Commerce.App.Enum.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {

    private PaymentMethod paymentMethod;

}
