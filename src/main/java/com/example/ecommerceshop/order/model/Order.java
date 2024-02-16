package com.example.ecommerceshop.order.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotBlank
  @Column(name = "user_id")
  private Integer userId;

  @NotBlank
  @Column(name = "total_cost")
  private double totalCost;

  private ZonedDateTime date;

  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "orderId")
  private List<OrderItem> orderItems;

  public Order(Integer userId, double totalCost) {
    this.userId = userId;
    this.totalCost = totalCost;
  }
}
