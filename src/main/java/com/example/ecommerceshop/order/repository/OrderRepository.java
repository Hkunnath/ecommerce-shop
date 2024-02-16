package com.example.ecommerceshop.order.repository;

import com.example.ecommerceshop.order.model.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
  List<Order> findByUserId(Integer Id);
}
