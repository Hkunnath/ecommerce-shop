package com.example.ecommerceshop.product.exception;

public class ProductAlreadyExistException extends RuntimeException {
  public ProductAlreadyExistException() {
    super("Product already exists");
  }
}
