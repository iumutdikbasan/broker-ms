package com.iumutdikbasan.productservice.service.impl;

import com.iumutdikbasan.productservice.dto.ProductRequest;
import com.iumutdikbasan.productservice.dto.ProductResponse;
import com.iumutdikbasan.productservice.model.Product;
import com.iumutdikbasan.productservice.repository.ProductRepository;
import com.iumutdikbasan.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);
        log.info("Product {} is saved", product.getId());
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

    @Override
    public void deleteProductById(String id) {
        log.info("Product id: {}", id);
        log.info("Deleting Product with id: {}", id);
        productRepository.deleteById(id);

    }

}
