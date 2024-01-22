package com.example.ecommerceshop.cart.model;

import com.example.ecommerceshop.product.model.Product;
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

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @NotBlank
    private Integer quantity;

    @NotBlank
    @Column(name = "item_cost")
    private double cartItemCost;

    public CartItem(Cart cart, Product product, Integer quantity, double cartItemCost) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
        this.cartItemCost = cartItemCost;
    }
}
