package com.example.ecommerceshop.product.service;

import com.example.ecommerceshop.product.dto.request.ProductDetailsDto;
import com.example.ecommerceshop.product.dto.response.ProductDto;
import com.example.ecommerceshop.product.exception.ProductNotFoundException;
import com.example.ecommerceshop.product.model.Product;
import com.example.ecommerceshop.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
        return new ProductDto(product.getId(), product.getProductName());
    }

    public ProductDto updateProduct(ProductDetailsDto productDetailsDto, Integer id) {
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            log.error("Product not found");
            throw new ProductNotFoundException();
        }
        Product actualProduct = product.get();
        actualProduct.setProductName(productDetailsDto.getProductName());
        actualProduct.setProductDecription(productDetailsDto.getProductDescription());
        actualProduct.setProductPrice(productDetailsDto.getProductPrice());
        actualProduct.setStockQuantity(productDetailsDto.getStockQuantity());
        productRepository.save(actualProduct);
        log.info("Product Updated Successfully");
        return new ProductDto(actualProduct.getId(), actualProduct.getProductName());
    }

    public ResponseEntity<String> deleteProduct(Integer id) {
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            log.error("Product not found");
            throw new ProductNotFoundException();
        }
        productRepository.delete(product.get());
        log.info("Product Deleted Successfully");
        return new ResponseEntity<>("Product Deleted", HttpStatus.OK);
    }
}
