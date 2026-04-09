package com.codelab.order_service.client;


import com.codelab.order_service.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="product-service",configuration = FeignConfig.class)

public interface ProductClient {


    @GetMapping("/products/{id}")
    public ProductResponse getProductById(@PathVariable Long id);

    @PutMapping("/products/{id}/reduce")
    void reduceQuantity(@PathVariable Long id,
                        @RequestParam int quantity);
}
