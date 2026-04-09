package com.codelab.payment_service.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PaymentRequest {

    @NotNull(message = "OrderId is required")
    private Long orderId;

    @Positive(message = "Amount must be greater than zero")
    private Double amount;
}

