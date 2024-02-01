package com.example.ecommerceshop.order.controller;

import com.example.ecommerceshop.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.ecommerceshop.user.auth.JwtUtil.TOKEN_HEADER;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public void getOrder(@RequestHeader(TOKEN_HEADER) String bearerToken){
        orderService.getOrder(bearerToken);
    }
}
