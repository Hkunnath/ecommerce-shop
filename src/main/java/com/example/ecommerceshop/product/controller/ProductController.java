package com.example.ecommerceshop.product.controller;

import com.example.ecommerceshop.product.dto.response.ProductResponseDto;
import com.example.ecommerceshop.product.model.Product;
import com.example.ecommerceshop.product.dto.request.ProductDetailsDto;
import com.example.ecommerceshop.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> findAllProducts() {
        return productService.findAllProducts();
    }

    @GetMapping({"/{id}"})
    public ProductResponseDto findProduct(@PathVariable Integer id){
        return productService.findProduct(id);
    }

    @PostMapping
    public ProductResponseDto addProduct(@RequestBody ProductDetailsDto productDetailsDto) {
        return productService.addProduct(productDetailsDto);
    }

    @PutMapping("/{id}")
    public ProductResponseDto updateProduct(@RequestBody ProductDetailsDto productDetailsDto, @PathVariable Integer id){
        return productService.updateProduct(productDetailsDto,id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id){
        productService.deleteProduct(id);
        return new ResponseEntity<>("Product Deleted",HttpStatus.OK);
    }
}
