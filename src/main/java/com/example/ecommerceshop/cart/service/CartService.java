package com.example.ecommerceshop.cart.service;

import com.example.ecommerceshop.cart.dto.CartDto;
import com.example.ecommerceshop.cart.model.Cart;
import com.example.ecommerceshop.cart.model.CartItem;
import com.example.ecommerceshop.cart.repository.CartItemRepository;
import com.example.ecommerceshop.cart.repository.CartRepository;
import com.example.ecommerceshop.product.dto.response.ProductResponseDto;
import com.example.ecommerceshop.product.model.Product;
import com.example.ecommerceshop.user.dto.request.LoginRequest;
import com.example.ecommerceshop.user.dto.response.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final WebClient webClient;
    private final CartItemRepository cartItemRepository;
    public void addService(@RequestBody CartDto cartDto){
        UserDto userDto = getUserDto(cartDto.getUserName(), cartDto.getPassword());
        Cart cart = new Cart(userDto.getUserId(),0);
        cartRepository.save(cart);
        List<CartItem> cartItems = cartDto.getProductList().stream().map(
                productDto -> {
                    final Integer id = productDto.getProductId();
                    ProductResponseDto productResponseDto = webClient.get()
                            .uri("/api/products/{id}", id)
                            .header("Authorization", "Bearer "+userDto.getJwt())
                            .retrieve()
                            .bodyToMono(ProductResponseDto.class)
                            .block();
                    Product product = new Product(productResponseDto.getProductId(), productResponseDto.getProductName(), productResponseDto.getProductDescription(),
                            productResponseDto.getProductPrice(), productResponseDto.getStockQuantity());
                    return new CartItem(cart, product, productDto.getProductQty(), productResponseDto.getProductPrice() * productDto.getProductQty());

                }
        ).collect(Collectors.toList());
        cartItemRepository.saveAll(cartItems);
        double cartTotalCost = cartItems.stream().map(CartItem::getCartItemCost).mapToDouble(Double::doubleValue).sum();
        cart.setTotalCost(cartTotalCost);
        cartRepository.save(cart);
    }

    private UserDto getUserDto(String userName, String password){
        return webClient.post()
                .uri("api/users/login")
                .body(BodyInserters.fromValue(LoginRequest.builder().username(userName).password(password).build()))
                .retrieve()
                .bodyToMono(UserDto.class)
                .block();
    }
}
