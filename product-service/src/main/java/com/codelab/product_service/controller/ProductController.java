package com.codelab.product_service.controller;

import com.codelab.product_service.dto.ProductRequest;
import com.codelab.product_service.dto.ProductResponce;
import com.codelab.product_service.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin("*")

public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponce> addProduct(@Valid @RequestBody ProductRequest request) {
        ProductResponce response = productService.addProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponce>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponce> getProductById(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponce> updateProduct(@PathVariable Long productId,
                                                         @Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.updateProduct(productId, request));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);

        return ResponseEntity.ok("Product deleted successfully");

    }


    @PutMapping("/{productId}/reduce")
    public ResponseEntity<String> reduceQuantity(
            @PathVariable Long productId,
            @RequestParam int quantity) {

        productService.reduceQuantity(productId, quantity);

        return ResponseEntity.ok(
                "Quantity reduced successfully for productId: " + productId
        );
    }
}




//package com.codelab.product_service.controller;
//
//import com.codelab.product_service.entity.Product;
//import com.codelab.product_service.service.ProductService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@CrossOrigin("*")
//@RestController
//@RequestMapping("/products")
//@Tag(name = "Product Controller", description = "APIs for managing products")
//public class ProductController {
//
//    @Autowired
//    private ProductService productService;
//
//    @Operation(
//            summary = "Create Product",
//            description = "Creates a new product and saves it in the database"
//    )
//    @ApiResponse(responseCode = "201", description = "Product created successfully")
//    @PostMapping
//    public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product) {
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(productService.addProduct(product));
//
//    }
//
//    @Operation(
//            summary = "Get product by ID",
//            description = "Fetch a single product from the database using its ID"
//    )
//    @ApiResponse(responseCode = "200", description = "Product found")
//    @ApiResponse(responseCode = "404", description = "Product not found")
//    @GetMapping("/{id}")
//    public ResponseEntity<Product> getSingleProduct(@PathVariable Long id) {
//        return ResponseEntity.ok(productService.getProductById(id));
//    }
//
//
//    @Operation(
//            summary = "Get all products",
//            description = "Fetch the list of all available products"
//    )
//    @ApiResponse(responseCode = "200", description = "List of products fetched successfully")
//    @GetMapping
//    public ResponseEntity<List<Product>> getAllProducts() {
//        return ResponseEntity.ok(productService.getAllProducts());
//    }
//
//
//    @Operation(
//            summary = "Update product",
//            description = "Update product details using product ID"
//    )
//    @ApiResponse(responseCode = "200", description = "Product updated successfully")
//    @ApiResponse(responseCode = "404", description = "Product not found")
//    @PutMapping("/{id}")
//    public ResponseEntity<Product> updateProduct( @Valid @RequestBody Product product,
//                                                 @PathVariable Long id) {
//        return ResponseEntity.ok(productService.updateProduct(product, id));
//    }
//
//
//    @Operation(
//            summary = "Delete product",
//            description = "Delete a product from the database using its ID"
//    )
//    @ApiResponse(responseCode = "204", description = "Product deleted successfully")
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
//        productService.deleteProduct(id);
//        return ResponseEntity.noContent().build();
//    }
//}
//@RestController
//@RequestMapping("/products")
//public class ProductController {
//
//    @Autowired
//    private ProductService productService;
//
//    @Operation(summary = "Create Product")
//    @PostMapping("/add")
//    public ResponseEntity<Product> addProduct(@RequestBody Product product)
//    {
//
//        return ResponseEntity.ok(productService.addProduct(product));
//    }
//
//    @Operation(
//            summary = "Get product by ID",
//            description = "Fetch a single product from the database using its ID"
//    )
//    @GetMapping("/get/{id}")
//    public ResponseEntity<Product>getSingleProduct(@PathVariable Long id){
//
//      return  ResponseEntity.ok(productService.getProductById(id));
//    }
//
//    @Operation(
//            summary = "Get all products",
//            description = "Fetch the list of all available products"
//    )
// @GetMapping("/AllProduct")
//public ResponseEntity<List<Product>>getAllProducts()
//{
//    return ResponseEntity.ok(productService.getAllProducts());
//}
//
//    @Operation(
//            summary = "Update product",
//            description = "Update product details using product ID"
//    )
// @PutMapping("/update/{id}")
// public ResponseEntity<Product>updateProduct(@RequestBody Product product ,@PathVariable Long id){
//
//        return ResponseEntity.ok(productService.updateProduct(product,id));
// }
//
//    @Operation(
//            summary = "Delete product",
//            description = "Delete a product from the database using its ID"
//    )
// @DeleteMapping("/delete/{id}")
//  public ResponseEntity<String>deleteProduct(@PathVariable Long id){
//
//        productService.deleteProduct(id);
//
//        return ResponseEntity.ok("Product deleted successfully");
//  }
//}
