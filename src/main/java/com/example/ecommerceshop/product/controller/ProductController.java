package com.example.ecommerceshop.product.controller;

import com.example.ecommerceshop.product.model.Product;
import com.example.ecommerceshop.product.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
 private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/view")
    public List<Product> findAllProducts(){
        return productService.findAllProducts();
    }
}
