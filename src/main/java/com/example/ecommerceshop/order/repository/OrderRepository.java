package com.example.ecommerceshop.order.repository;

import com.example.ecommerceshop.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.relational.core.sql.In;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Optional<Order> findByUserId(Integer id);
}
