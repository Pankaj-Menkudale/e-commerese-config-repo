package com.codelab.product_service.service;

import com.codelab.product_service.dto.ProductRequest;
import com.codelab.product_service.dto.ProductResponce;

import java.util.List;

public interface ProductService {

    /**
     * Add a new product
     * @param request ProductRequest containing product details
     * @return ProductResponse with saved product details
     */
    ProductResponce addProduct(ProductRequest request);

    /**
     * Get all products
     * @return List of ProductResponse
     */
    List<ProductResponce> getAllProducts();

    /**
     * Get a product by ID
     * @param id Product ID
     * @return ProductResponse
     */
    ProductResponce getProductById(Long id);

    /**
     * Update a product
     * @param id Product ID to update
     * @param request ProductRequest containing new values
     * @return Updated ProductResponse
     */
    ProductResponce updateProduct(Long id, ProductRequest request);

    /**
     * Delete a product by ID
     * @param id Product ID to delete
     */
    void deleteProduct(Long productId);

    public void reduceQuantity(Long productId, int quantity);

}
