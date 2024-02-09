package com.example.ecommerceshop.cart.repository;

import com.example.ecommerceshop.cart.model.Cart;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {
  Optional<Cart> findByUserId(Integer id);
}
