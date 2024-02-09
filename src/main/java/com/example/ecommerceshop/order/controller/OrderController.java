package com.example.ecommerceshop.order.controller;

import com.example.ecommerceshop.order.dto.OrderDto;
import com.example.ecommerceshop.order.model.Order;
import com.example.ecommerceshop.order.service.OrderService;
import com.example.ecommerceshop.user.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public List<Order> getOrder(@AuthenticationPrincipal UserDetails userDetails) {
            return orderService.getOrder((CustomUserDetails) userDetails);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails instanceof CustomUserDetails) {
            orderService.placeOrderFromCart((CustomUserDetails) userDetails);
        }
    }
    @PutMapping("/changestatus")
    public void changeOrderStatus(Integer orderId, String status){
        orderService.changeOrderStatus(orderId,status);
    }
}
