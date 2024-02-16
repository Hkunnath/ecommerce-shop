package com.example.ecommerceshop.cart.repository;

import com.example.ecommerceshop.cart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {}
