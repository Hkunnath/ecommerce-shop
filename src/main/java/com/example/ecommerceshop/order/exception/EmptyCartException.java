package com.example.ecommerceshop.order.exception;

public class EmptyCartException extends RuntimeException{
    public EmptyCartException(){
        super("Attempted to place an order with zero items in the cart");
    }
}
