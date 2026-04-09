package com.codelab.order_service.controller;

import com.codelab.order_service.dto.OrderRequest;
import com.codelab.order_service.dto.OrderResponse;
import com.codelab.order_service.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/orders")
@RestController
@CrossOrigin("*")
public class OrderController {


    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(
            summary = "Place a new order",
            description = "Creates a new order based on the given request details and returns order confirmation"
    )
    @ApiResponse(responseCode = "200", description = "Order placed successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request data")
    @PostMapping("/requestOrder")
    public ResponseEntity<OrderResponse> placeOrder( @Valid @RequestBody OrderRequest request) {

        System.out.println("inside order controller");

        return ResponseEntity.ok(orderService.placeOrder(request));
    }
}
