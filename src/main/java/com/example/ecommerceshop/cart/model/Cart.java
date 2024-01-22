package com.example.ecommerceshop.cart.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(name = "user_id")
    private Integer userId;

    @NotBlank
    @Column(name = "total_cost" )
    private double totalCost;

    public Cart() {
    }

    public Cart(Integer userId, double totalCost) {
        this.userId = userId;
        this.totalCost = totalCost;
    }
}
