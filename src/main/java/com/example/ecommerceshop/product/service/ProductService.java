package com.example.ecommerceshop.product.service;

import com.example.ecommerceshop.product.model.Product;
import com.example.ecommerceshop.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public List<Product> findAllProducts(){
        return productRepository.findAll();
    }
}
