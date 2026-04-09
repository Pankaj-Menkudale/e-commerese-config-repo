package com.codelab.order_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Positive(message = "Product ID must be greater than zero")
    private Long productId;

    @Positive(message = "Quantity must be greater than zero")
    private int quantity;

    @Positive(message = "Total price must be greater than zero")
    private double totalPrice;
}




//package com.codelab.order_service.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//@Entity
//@Data
//@Table(name="orders")
//public class Order {
//
//    @Id
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
//    private int id;
//
//    private int productId;
//
//    private int quantity;
//
//    private double totalPrice;
//}
