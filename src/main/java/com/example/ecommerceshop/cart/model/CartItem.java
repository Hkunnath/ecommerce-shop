package com.example.ecommerceshop.cart.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart_items")
@Data
@NoArgsConstructor
public class CartItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private Integer cartId;
  @NotBlank private Integer productId;

  @NotBlank private Integer quantity;

  @NotBlank
  @Column(name = "item_cost")
  private double cartItemCost;

  public CartItem(Integer cartId, Integer productId, Integer quantity, double cartItemCost) {
    this.cartId = cartId;
    this.productId = productId;
    this.quantity = quantity;
    this.cartItemCost = cartItemCost;
  }
}
