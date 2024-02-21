package com.example.ecommerceshop.order.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
public class OrderItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private Integer orderId;

  private Integer quantity;

  private Integer productId;

  @NotBlank
  @Column(name = "item_cost")
  private double orderItemCost;
}
