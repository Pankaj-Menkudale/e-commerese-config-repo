package com.codelab.product_service.dto;

import com.codelab.product_service.entity.Product;
import org.springframework.stereotype.Component;

@Component   // 🔥 ADD THIS LINE
public class ProductMapper {

    public Product toEntity(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        return product;

    }


    public ProductResponce toResponse(Product product) {
        ProductResponce response = new ProductResponce();
        response.setProductId(product.getProductId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setQuantity(product.getQuantity());
        return response;
    }
}
