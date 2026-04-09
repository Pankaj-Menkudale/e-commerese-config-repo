

package com.codelab.order_service.serviceImpl;

import com.codelab.order_service.client.ProductClient;
import com.codelab.order_service.dto.OrderRequest;
import com.codelab.order_service.dto.OrderResponse;
import com.codelab.order_service.dto.ProductResponse;
import com.codelab.order_service.entity.Order;
import com.codelab.order_service.exception.OrderNotFoundException;
import com.codelab.order_service.repository.OrderRepository;
import com.codelab.order_service.service.OrderService;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;


@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repo;
    private final ProductClient productClient;

    private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);

    public OrderServiceImpl(OrderRepository repo, ProductClient productClient) {
        this.repo = repo;
        this.productClient = productClient;
    }



    @Override
    @Transactional
    public OrderResponse placeOrder(OrderRequest orderRequest) {

        System.out.println("yes it reaching out to me ....");
        logger.info("Placing order for productId: {} quantity: {}",
                orderRequest.getProductId(), orderRequest.getQuantity());

        if (orderRequest.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        ProductResponse product;

        // ✅ Step 1: Get product
        try {
            product = productClient.getProductById(orderRequest.getProductId());
        } catch (feign.FeignException.NotFound ex) {
            throw new OrderNotFoundException(
                    "Product not found with id: " + orderRequest.getProductId());
        }

        // ✅ Step 2: Check stock
        if (product.getQuantity() < orderRequest.getQuantity()) {
            throw new RuntimeException("Insufficient stock");
        }

        // ✅ Step 3: Calculate total
        double total = product.getPrice() * orderRequest.getQuantity();

        // ✅ Step 4: Reduce quantity
        productClient.reduceQuantity(
                orderRequest.getProductId(),
                orderRequest.getQuantity()
        );

        // ✅ Step 5: Save order
        Order order = new Order();
        order.setProductId(orderRequest.getProductId());
        order.setQuantity(orderRequest.getQuantity());
        order.setTotalPrice(total);

        Order savedOrder = repo.save(order);

        logger.info("Order placed successfully with id: {}", savedOrder.getId());

        return new OrderResponse(
                savedOrder.getId(),
                savedOrder.getProductId(),
                savedOrder.getQuantity(),
                savedOrder.getTotalPrice(),
                "Order Placed Successfully"
        );
    }
//
//    @Override
//    @Transactional
//    public OrderResponse palceOrder(OrderRequest orderRequest) {
//
//        logger.info("Placing order for productId: {} quantity: {}",
//                orderRequest.getProductId(), orderRequest.getQuantity());
//
//        // ✅ Validation
//        if (orderRequest.getQuantity() <= 0) {
//            throw new IllegalArgumentException("Quantity must be greater than 0");
//        }
//
//        // ✅ Call Product Service
//        ProductResponse product = productClient.reduceQuantity(orderRequest.getProductId());
//
//        // ✅ Handle product not found
////        if (product == null) {
////            throw new OrderNotFoundException(
////                    "Product not found with id: " + orderRequest.getProductId());
////        }
//        try {
//            product = productClient.reduceQuantity(orderRequest.getProductId());
//        } catch (feign.FeignException.NotFound ex) {
//            throw new OrderNotFoundException(
//                    "Product not found with id: " + orderRequest.getProductId());
//        }
//
//
//        // ✅ Business logic
//        double total = product.getPrice() * orderRequest.getQuantity();
//
//        Order order = new Order();
//        order.setProductId(orderRequest.getProductId());
//        order.setQuantity(orderRequest.getQuantity());
//        order.setTotalPrice(total);
//
//        Order savedOrder = repo.save(order);
//
//        logger.info("Order placed successfully with id: {}", savedOrder.getId());
//
//        return new OrderResponse(
//                savedOrder.getId(),
//                savedOrder.getProductId(),
//                savedOrder.getQuantity(),
//                savedOrder.getTotalPrice(),
//                "Order Placed Successfully"
//        );
//    }


}
//
//@Service
//public class OrderServiceImpl implements OrderService {
//
//    private final OrderRepository repo;
//    private final ProductClient productClient;
//
//    private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);
//
//    public OrderServiceImpl(OrderRepository repo, ProductClient productClient) {
//        this.repo = repo;
//        this.productClient = productClient;
//    }
//
//    @Override
//    public OrderResponse palceOrder(OrderRequest orderRequest) {
//        logger.info("Placing order for productId: {} quantity: {}", orderRequest.getProductId(), orderRequest.getQuantity());
//        try {
//            ProductResponse product = productClient.getProductById(orderRequest.getProductId());
//            if (product == null) {
//                logger.warn("Product not found for id {}", orderRequest.getProductId());
//                throw new RuntimeException("Product not found");
//            }
//
//            double total = product.getPrice() * orderRequest.getQuantity();
//
//            Order order = new Order();
//            order.setProductId(orderRequest.getProductId());
//            order.setQuantity(orderRequest.getQuantity());
//            order.setTotalPrice(total);
//
//            repo.save(order);
//            logger.info("Order placed successfully with id: {}", order.getId());
//
//            return new OrderResponse(order.getId(),order.getProductId(),order.getQuantity(),order.getTotalPrice(), "Order Placed Successfully");
//        } catch (Exception e) {
//            logger.error("Error placing order for productId {}: {}", orderRequest.getProductId(), e.getMessage(), e);
//            throw e;
//        }
//    }
//}

