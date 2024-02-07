package com.example.ecommerceshop.order.controller;

import com.example.ecommerceshop.order.service.OrderService;
import com.example.ecommerceshop.user.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public void getOrder(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails instanceof CustomUserDetails) {
            orderService.getOrder((CustomUserDetails) userDetails);
        }
    }

    @PostMapping
    public void createOrder(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails instanceof CustomUserDetails) {
            orderService.placeOrderFromCart((CustomUserDetails) userDetails);
        }
    }
}
