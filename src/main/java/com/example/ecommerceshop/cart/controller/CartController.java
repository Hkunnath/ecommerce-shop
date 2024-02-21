package com.example.ecommerceshop.cart.controller;

import com.example.ecommerceshop.cart.dto.request.ProductDto;
import com.example.ecommerceshop.cart.dto.response.CartDto;
import com.example.ecommerceshop.cart.service.CartService;
import com.example.ecommerceshop.user.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
  public ResponseEntity<CartDto> getCart(@AuthenticationPrincipal UserDetails userDetails) {
    final CartDto cart = cartService.getCart((CustomUserDetails) userDetails);
    return ResponseEntity.ok(cart);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void addProductsToCart(
      @AuthenticationPrincipal UserDetails userDetails, @RequestBody ProductDto productDto) {
    cartService.addProductsToCart((CustomUserDetails) userDetails, productDto);
  }

  @DeleteMapping("/removeproduct/{id}")
  public ResponseEntity<Object> removeProductsFromCart(
      @AuthenticationPrincipal UserDetails userDetails, @PathVariable Integer id) {
    cartService.removeProductsFromCart((CustomUserDetails) userDetails, id);
    return ResponseEntity.noContent().build();
  }
}
