package com.example.ecommerceshop.cart.controller;

import com.example.ecommerceshop.cart.dto.request.CartRequestDto;
import com.example.ecommerceshop.cart.dto.response.CartDto;
import com.example.ecommerceshop.cart.service.CartService;
import com.example.ecommerceshop.user.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
@Slf4j
public class CartController {
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<CartDto> getCart(@AuthenticationPrincipal UserDetails userDetails){
        final CartDto cart = cartService.getCart((CustomUserDetails) userDetails);
        return ResponseEntity.ok(cart);
    }
}
