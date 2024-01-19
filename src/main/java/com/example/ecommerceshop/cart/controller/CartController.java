package com.example.ecommerceshop.cart.controller;

import com.example.ecommerceshop.cart.dto.CartDto;
import com.example.ecommerceshop.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
@Slf4j
public class CartController {
    private final CartService cartService;

    @PostMapping
    public void addCartItem(@RequestBody CartDto cartDto){
        log.info("Cart Item created");
        cartService.addService(cartDto);
    }
}
