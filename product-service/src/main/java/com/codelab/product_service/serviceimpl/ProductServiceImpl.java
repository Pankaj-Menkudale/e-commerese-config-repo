
package com.codelab.product_service.serviceimpl;

import com.codelab.product_service.dto.ProductMapper;
import com.codelab.product_service.dto.ProductRequest;
import com.codelab.product_service.dto.ProductResponce;
import com.codelab.product_service.entity.Product;
import com.codelab.product_service.exception.ProductNotFoundException;
import com.codelab.product_service.repository.ProductRepository;
import com.codelab.product_service.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper mapper;
    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @Override
    public ProductResponce addProduct(ProductRequest request) {
        Product product = mapper.toEntity(request);
        Product saved = productRepository.save(product);
        logger.info("Product created with id: {}", saved.getProductId());
        return mapper.toResponse(saved);
    }

    @Override
    public List<ProductResponce> getAllProducts() {
        logger.info("Fetching all products");
        return productRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponce getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));
        logger.info("Fetched product with id: {}", productId);
        return mapper.toResponse(product);
    }

    @Override
    public ProductResponce updateProduct(Long productId, ProductRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());

        Product updated = productRepository.save(product);
        logger.info("Updated product with id: {}", productId);
        return mapper.toResponse(updated);
    }

    @Override
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));
        productRepository.delete(product);
        logger.info("Deleted product with id: {}", productId);
    }


    public void reduceQuantity(Long productId, int quantity) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }

        product.setQuantity(product.getQuantity() - quantity);

        productRepository.save(product);
    }
}
//
//import com.codelab.product_service.dto.ProductMapper;
//import com.codelab.product_service.dto.ProductRequest;
//import com.codelab.product_service.dto.ProductResponce;
//import com.codelab.product_service.entity.Product;
//import com.codelab.product_service.exception.ProductNotFoundException;
//import com.codelab.product_service.repository.ProductRepository;
//import com.codelab.product_service.service.ProductService;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.stereotype.Service;
//import org.springframework.validation.annotation.Validated;
//
//import java.util.List;
//
//@Service
//@Validated
//public class ProductServiceImpl implements ProductService {
//
//    //private final ProductRepository productRepository;
//    private static final Logger logger = LogManager.getLogger(ProductServiceImpl.class);
//
//
//    private final ProductRepository productRepository;
//    private final ProductMapper mapper; // <-- This is what 'mapper' is
//
//
//
//    public ProductServiceImpl(ProductRepository productRepository, ProductMapper mapper) {
//        this.productRepository = productRepository;
//        this.mapper = mapper;
//    }
////    public ProductServiceImpl(ProductRepository productRepository) {
////        this.productRepository = productRepository;
////    }
//
////    @Override
////    public Product addProduct(Product product) {
////        logger.info("Adding new product: {}", product.getName());
////        return productRepository.save(product);
////    }
//
//
//    @Override
//    public ProductResponce addProduct(ProductRequest request) {
//
//        Product product=mapper.toEntity(request);
//        Product saved=productRepository.save(product);
//
//        return mapper.toResponse(saved);
//    }
//
//    @Override
//    public List<ProductResponce> getAllProducts() {
//        logger.info("Fetching all products");
//        return productRepository.findAll();
//    }
//
//    @Override
//    public ProductResponce getProductById(Long id) {
//        logger.info("Fetching product by id: {}", id);
//        try {
//            return productRepository.findById(id)
//                    .orElseThrow(() -> {
//                        logger.warn("Product not found with id {}", id);
//                        return new ProductNotFoundException("Product not found with id " + id);
//                    });
//        } catch (Exception e) {
//            logger.error("Error fetching product with id {}: {}", id, e.getMessage(), e);
//            throw e;
//        }
//    }
//
//    @Override
//    public ProductResponce updateProduct(Product product, long id) {
//        logger.info("Updating product with id: {}", id);
//        Product existing = getProductById(id);
//        existing.setName(product.getName());
//        existing.setPrice(product.getPrice());
//        existing.setQuantity(product.getQuantity());
//        existing.setDescription(product.getDescription());
//
//        Product updated = productRepository.save(existing);
//        logger.info("Product updated successfully with id: {}", id);
//        return updated;
//    }
//
//    @Override
//    public void deleteProduct(Long id) {
//        logger.info("Deleting product with id: {}", id);
//        Product existing = getProductById(id);
//        productRepository.delete(existing);
//        logger.info("Product deleted successfully with id: {}", id);
//    }
//}




//package com.codelab.product_service.serviceimpl;
//
//import com.codelab.product_service.entity.Product;
//import com.codelab.product_service.exception.ProductNotFoundException;
//import com.codelab.product_service.repository.ProductRepository;
//import com.codelab.product_service.service.ProductService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class ProductServiceImpl implements ProductService {
//
//    private final ProductRepository productRepository;
//
//    // ✅ Constructor Injection (BEST PRACTICE)
//    public ProductServiceImpl(ProductRepository productRepository) {
//        this.productRepository = productRepository;
//    }
//
//    @Override
//    public Product addProduct(Product product) {
//
//        return productRepository.save(product);
//    }
//
//    @Override
//    public List<Product> getAllProducts() {
//        return productRepository.findAll();
//    }
//
//    @Override
//    public Product getProductById(Long id) {
//        return productRepository.findById(id).orElseThrow(()->new ProductNotFoundException("Product not foudn with id "+ id));
//
//
//    }
//
//    @Override
//    public Product updateProduct(Product product, long id) {
//
//        Product existing=getProductById(id);
//        existing.setName(product.getName());
//        existing.setPrice(product.getPrice());
//        existing.setQuantity(product.getQuantity());
//        existing.setDescription(product.getDescription());
//        existing.setPrice(product.getPrice());
//
//
//        return productRepository.save(existing) ;
//    }
//
//    @Override
//    public void deleteProduct(Long id) {
//
//        Product existing=getProductById(id);
//        productRepository.delete(existing);
//
//    }
//}
