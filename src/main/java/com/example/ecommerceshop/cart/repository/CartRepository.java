package com.example.ecommerceshop.cart.repository;

import com.example.ecommerceshop.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Integer> {
}
