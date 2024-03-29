package com.example.ecommerceshop.product.controller;

import com.example.ecommerceshop.product.dto.request.ProductDetailsDto;
import com.example.ecommerceshop.product.dto.response.ProductResponseDto;
import com.example.ecommerceshop.product.model.Product;
import com.example.ecommerceshop.product.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public Page<Product> findAllProducts(@RequestParam int page, @RequestParam int size) {
    return productService.findAllProducts(page, size);
  }

  @GetMapping({"/{id}"})
  public ProductResponseDto findProduct(@PathVariable Integer id) {
    return productService.findProduct(id);
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ProductResponseDto addProduct(@RequestBody ProductDetailsDto productDetailsDto) {
    return productService.addProduct(productDetailsDto);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ProductResponseDto updateProduct(
      @RequestBody ProductDetailsDto productDetailsDto, @PathVariable Integer id) {
    return productService.updateProduct(productDetailsDto, id);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
    productService.deleteProduct(id);
    return new ResponseEntity<>("Product Deleted", HttpStatus.OK);
  }
}
