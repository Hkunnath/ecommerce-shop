package com.example.ecommerceshop.order.service;

import com.example.ecommerceshop.order.repository.OrderRepository;
import com.example.ecommerceshop.user.auth.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    }
}
