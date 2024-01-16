package com.example.ecommerceshop.product.service;

import com.example.ecommerceshop.product.dto.request.ProductDetailsDto;
import com.example.ecommerceshop.product.dto.response.ProductDto;
import com.example.ecommerceshop.product.model.Product;
import com.example.ecommerceshop.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public ProductDto addProduct(ProductDetailsDto productDetailsDto) {
        Product product = new Product(productDetailsDto.getProductName(), productDetailsDto.getProductDescription(), productDetailsDto.getProductPrice(), productDetailsDto.getStockQuantity());
        product = productRepository.save(product);
        log.info("New product added to repository");
        return new ProductDto(product.getId(),product.getProductName());
    }
}
