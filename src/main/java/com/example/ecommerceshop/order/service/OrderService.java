package com.example.ecommerceshop.order.service;

import com.example.ecommerceshop.cart.model.Cart;
import com.example.ecommerceshop.cart.model.CartItem;
import com.example.ecommerceshop.cart.repository.CartRepository;
import com.example.ecommerceshop.order.mapper.CartItemTransformertoOrderItem;
//import com.example.ecommerceshop.order.mapper.CartToOrder;
import com.example.ecommerceshop.order.model.Order;
import com.example.ecommerceshop.order.model.OrderItem;
import com.example.ecommerceshop.order.model.OrderStatus;
import com.example.ecommerceshop.order.repository.OrderRepository;
import com.example.ecommerceshop.user.service.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final CartRepository cartRepository;
    private final CartItemTransformertoOrderItem cartItemToOrder;
    private final OrderRepository orderRepository;

    public void getOrder(CustomUserDetails customUserDetails) {
        Integer userId = customUserDetails.getUserId();
        log.info("User Id" + userId);
    }

    @Transactional
    public void placeOrderFromCart(CustomUserDetails customUserDetails) {
        Integer userId = customUserDetails.getUserId();
        log.info("User Id" + userId);
        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        if (optionalCart.isEmpty()) {
            log.info("No Cart found for associated user");
            throw new RuntimeException("Cart Not found ");
        }
        Order order = new Order(userId, 0);
        List<OrderItem> orderItemList = cartItemToOrder.toOrderItemList(optionalCart.get().getCartItems());
        double totalCost = orderItemList.stream().map(OrderItem::getOrderItemCost).mapToDouble(Double::doubleValue).sum();
        order.setOrderItems(orderItemList);
        order.setStatus(OrderStatus.PLACED);
        order.setDate(LocalDateTime.now());
        order.setTotalCost(totalCost);
        log.info("Order Placed" + order);
        orderRepository.save(order);
        cartRepository.delete(optionalCart.get());

    }
}
