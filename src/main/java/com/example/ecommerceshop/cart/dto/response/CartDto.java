package com.example.ecommerceshop.cart.dto.response;

import com.example.ecommerceshop.cart.model.CartItem;
import java.util.List;
import lombok.Data;

@Data
public class CartDto {
  private Integer id;
  private Integer userId;
  private double totalCost;
  private List<CartItem> cartItems;
}
