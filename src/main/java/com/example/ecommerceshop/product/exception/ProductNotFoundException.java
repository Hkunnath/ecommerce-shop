package com.example.ecommerceshop.product.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(){
     super("Product Not Found");
    }
}
