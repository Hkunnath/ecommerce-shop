package com.example.ecommerceshop.cart.service;

import com.example.ecommerceshop.cart.dto.request.CartRequestDto;
import com.example.ecommerceshop.cart.dto.response.CartDto;
import com.example.ecommerceshop.cart.mapper.CartTransformer;
import com.example.ecommerceshop.cart.model.Cart;
import com.example.ecommerceshop.cart.model.CartItem;
import com.example.ecommerceshop.cart.repository.CartRepository;
import com.example.ecommerceshop.product.dto.response.ProductResponseDto;
import com.example.ecommerceshop.product.service.ProductService;
import com.example.ecommerceshop.user.service.CustomUserDetails;
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
    private final CartTransformer cartTransformer;
    private final ProductService productService;

    public CartDto getCart(CustomUserDetails customUserDetails) {
        final Integer userId = customUserDetails.getUserId();
        Optional<Cart> cart = cartRepository.findByUserId(userId);
        if (cart.isEmpty()) {
            log.info("cart is empty");
            Cart newCreatedCart = new Cart(userId, 0);
            newCreatedCart = cartRepository.save(newCreatedCart);
            return cartTransformer.toDto(newCreatedCart);
        }
        return cartTransformer.toDto(cart.get());
    }

    public void createCart(CustomUserDetails customUserDetails, CartRequestDto cartRequestDto) {
        final Integer userId = customUserDetails.getUserId();
        double cartTotalCost = cartRequestDto.getProductList().stream().map(
                productDto -> {
                    final Integer productId = productDto.getProductId();
                    ProductResponseDto productResponseDto = productService.findProduct(productId);
                    productDto.setTotalCost(productResponseDto.getProductPrice() * productDto.getProductQty());
                    return productDto.getTotalCost();
                })
                .mapToDouble(Double::doubleValue).sum();
        Cart cart = new Cart(userId,cartTotalCost);
        List<CartItem> cartItems = cartRequestDto.getProductList().stream().map(
                productDto -> {
                    return new CartItem(cart.getId(), productDto.getProductId(), productDto.getProductQty(), productDto.getTotalCost());
                }).collect(Collectors.toList());
        cart.setCartItems(cartItems);
        cartRepository.save(cart);
    }
}
