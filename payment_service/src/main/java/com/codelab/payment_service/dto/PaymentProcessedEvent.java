package com.codelab.payment_service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentProcessedEvent {
    private Long orderId;
    private boolean success;
}