package com.codelab.order_service.service;

import com.codelab.order_service.dto.OrderRequest;
import com.codelab.order_service.dto.OrderResponse;

public interface OrderService {


    public OrderResponse placeOrder(OrderRequest orderRequest);

    }
