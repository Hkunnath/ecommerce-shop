package com.example.ecommerceshop.product.repository;

import com.example.ecommerceshop.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
  Page<Product> findAll(Pageable pageable);
}
