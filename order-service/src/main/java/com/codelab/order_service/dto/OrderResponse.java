package com.codelab.order_service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderResponse {
//    private int orderId;
//    private String message;

    private Long id;
    private Long productId;
    private int quantity;
    private double totalPrice;
    private String message;
}

