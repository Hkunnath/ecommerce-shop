package com.example.ecommerceshop.cart.exception;

public class CartNotFoundException extends RuntimeException{
    public CartNotFoundException(String message){
        super(message);
    }
}
