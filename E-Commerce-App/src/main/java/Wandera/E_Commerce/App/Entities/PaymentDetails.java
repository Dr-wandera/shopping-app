package Wandera.E_Commerce.App.Entities;

import Wandera.E_Commerce.App.Enum.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDetails {
    private String razorpayOrderId;
    private String  razorpayPaymentId;
    public String getRazorpaySignature;
    private PaymentStatus status;
}
