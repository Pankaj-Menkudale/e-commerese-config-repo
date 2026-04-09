package com.codelab.product_service.dto;

import lombok.Data;

@Data
public class ProductResponce {

    private Long productId;
    private String name;
    private double price;
    private String description;
    private int quantity;
}
