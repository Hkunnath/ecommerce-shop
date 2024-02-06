package com.example.ecommerceshop.cart.service;

import com.example.ecommerceshop.cart.dto.request.ProductDto;
import com.example.ecommerceshop.cart.dto.response.CartDto;
import com.example.ecommerceshop.cart.mapper.CartTransformer;
import com.example.ecommerceshop.cart.model.Cart;
import com.example.ecommerceshop.cart.model.CartItem;
import com.example.ecommerceshop.cart.repository.CartItemRepository;
import com.example.ecommerceshop.cart.repository.CartRepository;
import com.example.ecommerceshop.product.exception.ProductNotFoundException;
import com.example.ecommerceshop.product.model.Product;
import com.example.ecommerceshop.product.repository.ProductRepository;
import com.example.ecommerceshop.user.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {
    private final CartRepository cartRepository;
    private final CartTransformer cartTransformer;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public CartDto getCart(CustomUserDetails customUserDetails) {
        final Integer userId = customUserDetails.getUserId();
        Optional<Cart> cart = cartRepository.findByUserId(userId);
        if (cart.isEmpty()) {
            log.info("cart is empty");
            return null;
        }
        return cartTransformer.toDto(cart.get());
    }

    public void removeProductsFromCart(CustomUserDetails customUserDetails, ProductDto productDto) {
        final Integer userId = customUserDetails.getUserId();
        Optional<Cart> cart = cartRepository.findByUserId(userId);
        if (cart.isEmpty()) {
            throw new RuntimeException("Cart not found");
        }

        CartItem cartItemToRemove = null;
        for (CartItem cartItem : cart.get().getCartItems()) {
            if (cartItem.getProductId().equals(productDto.getProductId())) {
                cartItemToRemove = cartItem;
                break;
            }
        }

        if (cartItemToRemove != null) {
            cart.get().getCartItems().remove(cartItemToRemove);
            cartItemRepository.delete(cartItemToRemove);
            double cartTotalCost = cart.get().getTotalCost();
            cart.get().setTotalCost(cartTotalCost - cartItemToRemove.getCartItemCost());
            cartRepository.save(cart.get());
        } else {
            throw new ProductNotFoundException();
        }
    }


    public void addProductsToCart(CustomUserDetails customUserDetails, ProductDto productDto) {
        final Integer userId = customUserDetails.getUserId();
        Optional<Cart> cart = cartRepository.findByUserId(userId);
        Product product = productRepository.findById(productDto.getProductId())
                .orElseThrow(() -> new ProductNotFoundException());

        double productPrice = product.getProductPrice();
        CartItem newCartItem = null;

        if (!cart.isEmpty()) {
            Optional<CartItem> existingCartItemOptional = cart.get().getCartItems().stream()
                    .filter(cartItem -> cartItem.getProductId().equals(product.getId()))
                    .findFirst();
            double cartTotalCost = cart.get().getTotalCost();
            if (existingCartItemOptional.isPresent()) {
                CartItem existingCartItem = existingCartItemOptional.get();
                existingCartItem.setQuantity(existingCartItem.getQuantity() + productDto.getProductQty());
                existingCartItem.setCartItemCost(existingCartItem.getCartItemCost() + (productDto.getProductQty()) * product.getProductPrice());
                cart.get().setTotalCost(cartTotalCost + (productDto.getProductQty()) * product.getProductPrice());
            } else {
                newCartItem = setNewCartItem(productDto,productPrice);
                newCartItem.setCartId(cart.get().getId());
                cart.get().setTotalCost(cartTotalCost + newCartItem.getCartItemCost());
                cart.get().getCartItems().add(newCartItem);
            }
            cartRepository.save(cart.get());
        }else{
            Cart newCart = new Cart(userId, 0);
            ArrayList<CartItem> cartItems = new ArrayList<>();
            newCartItem = setNewCartItem(productDto,productPrice);
            newCartItem.setCartId(newCart.getId());
            cartItems.add(newCartItem);
            newCart.setCartItems(cartItems);
            newCart.setTotalCost(newCartItem.getCartItemCost());
            cartRepository.save(newCart);
        }
    }

    private CartItem setNewCartItem(ProductDto productDto,double productPrice){
        CartItem newCartItem = new CartItem();
        newCartItem.setProductId(productDto.getProductId());
        newCartItem.setQuantity(productDto.getProductQty());
        newCartItem.setCartItemCost((productDto.getProductQty()) * productPrice);
        return newCartItem;
    }
}


