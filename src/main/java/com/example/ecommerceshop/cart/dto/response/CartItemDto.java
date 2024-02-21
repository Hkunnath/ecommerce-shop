package com.example.ecommerceshop.cart.dto.response;

import lombok.Data;

@Data
public class CartItemDto {
  private Integer id;
  private Integer cartId;
  private Integer productId;
  private Integer quantity;
  private double cartItemCost;
}
