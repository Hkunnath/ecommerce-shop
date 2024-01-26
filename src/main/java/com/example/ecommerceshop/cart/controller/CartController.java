package com.example.ecommerceshop.cart.controller;

import com.example.ecommerceshop.cart.dto.request.CartRequestDto;
import com.example.ecommerceshop.cart.dto.response.CartDto;
import com.example.ecommerceshop.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.ecommerceshop.user.auth.JwtUtil.TOKEN_HEADER;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
@Slf4j
public class CartController {
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<CartDto> getCart(@RequestHeader(TOKEN_HEADER) String bearerToken){
        final CartDto cart = cartService.getCart(bearerToken);
        return ResponseEntity.ok(cart);
    }
}
