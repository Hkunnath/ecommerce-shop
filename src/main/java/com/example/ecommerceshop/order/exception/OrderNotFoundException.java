package com.example.ecommerceshop.order.exception;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(){
        super("No order found for this associated user");
    }
}
