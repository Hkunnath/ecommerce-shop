package com.example.ecommerceshop.cart.service;

import com.example.ecommerceshop.cart.dto.request.CartRequestDto;
import com.example.ecommerceshop.cart.dto.response.CartDto;
import com.example.ecommerceshop.cart.mapper.CartTransformer;
import com.example.ecommerceshop.cart.model.Cart;
import com.example.ecommerceshop.cart.model.CartItem;
import com.example.ecommerceshop.cart.repository.CartRepository;
import com.example.ecommerceshop.product.dto.response.ProductResponseDto;
import com.example.ecommerceshop.product.service.ProductService;
import com.example.ecommerceshop.user.auth.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {
    private final CartRepository cartRepository;
    private final JwtUtil jwtUtil;
    private final CartTransformer cartTransformer;

    public CartDto getCart(String bearerToken) {
        final String token = jwtUtil.getToken(bearerToken);
        final Integer userId = jwtUtil.getUserIdFromToken(token);
        Optional<Cart> cart = cartRepository.findByUserId(userId);
        if (cart.isEmpty()) {
            log.info("cart is empty");
            Cart newCreatedCart = new Cart(userId, 0);
            newCreatedCart = cartRepository.save(newCreatedCart);
            return cartTransformer.toDto(newCreatedCart);
        }
        return cartTransformer.toDto(cart.get());
    }
}
