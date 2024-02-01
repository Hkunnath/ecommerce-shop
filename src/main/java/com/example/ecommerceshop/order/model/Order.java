package com.example.ecommerceshop.order.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
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

    private LocalDateTime date;

    private OrderStatus status;

}
