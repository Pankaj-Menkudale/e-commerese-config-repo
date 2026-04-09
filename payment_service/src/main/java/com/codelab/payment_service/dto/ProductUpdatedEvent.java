package com.codelab.payment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductUpdatedEvent {
    private Long orderId;
    private Double amount; // total amount for this order
    private Long productId;
    private Integer quantity; // quantity purchased
}
