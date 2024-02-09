package com.example.ecommerceshop.cart.dto.response;

import com.example.ecommerceshop.cart.model.CartItem;
import lombok.Data;

import java.util.List;

@Data
public class CartDto {
    private Integer id;
    private Integer userId;
    private double totalCost;
    private List<CartItem> cartItems;
}
