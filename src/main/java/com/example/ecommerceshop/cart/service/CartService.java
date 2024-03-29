package com.example.ecommerceshop.cart.service;

import com.example.ecommerceshop.cart.dto.request.ProductDto;
import com.example.ecommerceshop.cart.dto.response.CartDto;
import com.example.ecommerceshop.cart.exception.CartNotFoundException;
import com.example.ecommerceshop.cart.mapper.CartTransformer;
import com.example.ecommerceshop.cart.model.Cart;
import com.example.ecommerceshop.cart.model.CartItem;
import com.example.ecommerceshop.cart.repository.CartItemRepository;
import com.example.ecommerceshop.cart.repository.CartRepository;
import com.example.ecommerceshop.product.exception.ProductNotFoundException;
import com.example.ecommerceshop.product.model.Product;
import com.example.ecommerceshop.product.repository.ProductRepository;
import com.example.ecommerceshop.product.service.ProductService;
import com.example.ecommerceshop.user.service.CustomUserDetails;
import java.util.ArrayList;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {
  private final CartRepository cartRepository;
  private final CartTransformer cartTransformer;
  private final CartItemRepository cartItemRepository;
  private final ProductRepository productRepository;
  private final ProductService productService;

  public CartDto getCart(CustomUserDetails customUserDetails) {
    final Integer userId = customUserDetails.getUserId();
    Optional<Cart> cart = cartRepository.findByUserId(userId);
    if (cart.isEmpty()) {
      log.error("No cart found for this associated user " + userId);
      throw new CartNotFoundException("CartNotFoundException");
    }
    return cartTransformer.toDto(cart.get());
  }

  public void removeProductsFromCart(CustomUserDetails customUserDetails, Integer id) {
    final Integer userId = customUserDetails.getUserId();
    Optional<Cart> cart = cartRepository.findByUserId(userId);
    if (cart.isEmpty()) {
      log.error("No cart found for this associated user " + userId);
      throw new CartNotFoundException("CartNotFoundException");
    }

    final Optional<CartItem> optionalCartItemToRemove =
        cart.get().getCartItems().stream()
            .filter(cartItem -> cartItem.getProductId().equals(id))
            .findFirst();

    if (optionalCartItemToRemove.isEmpty()) {
      log.error(String.format("No product with the following id %d", id));
      throw new ProductNotFoundException();
    }
    CartItem originalCartItem = optionalCartItemToRemove.get();
    cart.get().getCartItems().remove(originalCartItem);
    cartItemRepository.delete(originalCartItem);
    double cartTotalCost = cart.get().getTotalCost();
    cart.get().setTotalCost(cartTotalCost - originalCartItem.getCartItemCost());
    cartRepository.save(cart.get());
  }

  public void addProductsToCart(CustomUserDetails customUserDetails, ProductDto productDto) {
    final Integer userId = customUserDetails.getUserId();
    Optional<Cart> cart = cartRepository.findByUserId(userId);
    Product product =
        productRepository
            .findById(productDto.getProductId())
            .orElseThrow(() -> new ProductNotFoundException());

    double productPrice = product.getProductPrice();
    CartItem newCartItem = null;

    if (cart.isEmpty()) {
      Cart newCart = new Cart(userId, 0);
      ArrayList<CartItem> cartItems = new ArrayList<>();
      newCartItem = setNewCartItem(productDto, productPrice);
      newCartItem.setCartId(newCart.getId());
      cartItems.add(newCartItem);
      newCart.setCartItems(cartItems);
      newCart.setTotalCost(newCartItem.getCartItemCost());
      cartRepository.save(newCart);
      return;
    }

    Optional<CartItem> existingCartItemOptional =
        cart.get().getCartItems().stream()
            .filter(cartItem -> cartItem.getProductId().equals(product.getId()))
            .findFirst();
    double cartTotalCost = cart.get().getTotalCost();
    if (existingCartItemOptional.isPresent()) {
      CartItem existingCartItem = existingCartItemOptional.get();
      existingCartItem.setQuantity(existingCartItem.getQuantity() + productDto.getProductQty());
      productService.checkProductInventory(
          productDto.getProductId(), existingCartItem.getQuantity());
      existingCartItem.setCartItemCost(
          existingCartItem.getCartItemCost()
              + (productDto.getProductQty()) * product.getProductPrice());
      cart.get()
          .setTotalCost(cartTotalCost + (productDto.getProductQty()) * product.getProductPrice());
    } else {
      newCartItem = setNewCartItem(productDto, productPrice);
      newCartItem.setCartId(cart.get().getId());
      cart.get().setTotalCost(cartTotalCost + newCartItem.getCartItemCost());
      cart.get().getCartItems().add(newCartItem);
    }
    cartRepository.save(cart.get());
  }

  private CartItem setNewCartItem(ProductDto productDto, double productPrice) {
    CartItem newCartItem = new CartItem();
    productService.checkProductInventory(productDto.getProductId(), productDto.getProductQty());
    newCartItem.setProductId(productDto.getProductId());
    newCartItem.setQuantity(productDto.getProductQty());
    newCartItem.setCartItemCost((productDto.getProductQty()) * productPrice);
    return newCartItem;
  }
}
