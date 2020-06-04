package com.techcia.dtos.sales;
import com.mercadopago.*;
import com.mercadopago.resources.Payment;
import com.mercadopago.resources.datastructures.payment.Payer;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class PaymentDTO {
    @NotNull(message = "Token needs to be valid")
    private String token;
    @NotNull(message = "Description needs to be valid")
    private String description;
    @NotNull(message = "Installments needs to be valid")
    private int installments;
    @NotNull(message = "PaymentMethodId  needs to be valid")
    private String paymentMethodId;
    @NotNull(message = "Transaction Amount needs to be valid")
    private float transactionAmount;
    @NotNull(message = "Email is required")
    @Email(message = "Email needs to be valid")
    private String email;

    public Payment convertToEntity(){
        Payment payment = new Payment();
        payment.setTransactionAmount(189f)
                .setToken(this.getToken())
                .setDescription(this.getDescription())
                .setInstallments(this.getInstallments())
                .setPaymentMethodId(this.getPaymentMethodId())
                .setPayer(new Payer()
                        .setEmail(this.getEmail()));
        return payment;
    }
}
