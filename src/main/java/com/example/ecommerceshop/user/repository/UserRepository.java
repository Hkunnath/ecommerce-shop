package com.example.ecommerceshop.user.repository;

import com.example.ecommerceshop.user.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByUsername(String username);
}
