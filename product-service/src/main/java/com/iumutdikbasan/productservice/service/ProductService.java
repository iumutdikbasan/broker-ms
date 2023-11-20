package com.iumutdikbasan.productservice.service;

import com.iumutdikbasan.productservice.dto.ProductRequest;
import com.iumutdikbasan.productservice.dto.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    void createProduct(ProductRequest productRequest);
    List<ProductResponse> getAllProducts();
     void deleteProductById(String id);
}
