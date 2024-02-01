package com.example.ecommerceshop.order.service;

import com.example.ecommerceshop.cart.model.Cart;
import com.example.ecommerceshop.cart.repository.CartRepository;
import com.example.ecommerceshop.order.dto.OrderDto;
import com.example.ecommerceshop.order.model.Order;
import com.example.ecommerceshop.order.model.OrderStatus;
import com.example.ecommerceshop.order.repository.OrderRepository;
import com.example.ecommerceshop.user.auth.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final JwtUtil jwtUtil;
    public void getOrder(String bearerToken) {
        final String token = jwtUtil.getToken(bearerToken);
        final Integer userId = jwtUtil.getUserIdFromToken(token);
        log.info("User id" + userId);
        Optional<Order> order = orderRepository.findByUserId(userId);
        if(order.isEmpty()){
            log.info("no orders yet");
            }
        }
}
