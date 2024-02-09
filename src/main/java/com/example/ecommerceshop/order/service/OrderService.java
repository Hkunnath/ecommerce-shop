package com.example.ecommerceshop.order.service;

import com.example.ecommerceshop.cart.exception.CartNotFoundException;
import com.example.ecommerceshop.cart.model.Cart;
import com.example.ecommerceshop.cart.repository.CartRepository;
import com.example.ecommerceshop.order.dto.OrderDto;
import com.example.ecommerceshop.order.exception.OrderNotFoundException;
import com.example.ecommerceshop.order.mapper.CartItemTransformertoOrderItem;
import com.example.ecommerceshop.order.model.Order;
import com.example.ecommerceshop.order.model.OrderItem;
import com.example.ecommerceshop.order.model.OrderStatus;
import com.example.ecommerceshop.order.repository.OrderRepository;
import com.example.ecommerceshop.product.service.ProductService;
import com.example.ecommerceshop.user.service.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final CartRepository cartRepository;
    private final CartItemTransformertoOrderItem cartItemToOrder;
    private final OrderRepository orderRepository;
    private final ProductService productService;

    public List<Order> getOrder(CustomUserDetails customUserDetails) {
        Integer userId = customUserDetails.getUserId();
        log.info("User Id" + userId);
        List<Order> orderList = orderRepository.findByUserId(userId);
        if (orderList.isEmpty()) {
            throw new OrderNotFoundException();
        }
        return orderList;
    }

    @Transactional
    public void placeOrderFromCart(CustomUserDetails customUserDetails) {
        Integer userId = customUserDetails.getUserId();
        log.info("User Id" + userId);
        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        if (optionalCart.isEmpty()) {
            log.info("No Cart found for associated user");
            throw new CartNotFoundException(String.format("No Cart found for userID %d ", userId));
        }
        Order order = new Order(userId, 0);
        List<OrderItem> orderItemList = cartItemToOrder.toOrderItemList(optionalCart.get().getCartItems());

        orderItemList.stream().forEach(
                orderItem -> productService.updateProductInventory(orderItem.getProductId(), orderItem.getQuantity())
        );
        double totalCost = orderItemList.stream().map(OrderItem::getOrderItemCost).mapToDouble(Double::doubleValue).sum();
        order.setOrderItems(orderItemList);
        order.setStatus(OrderStatus.ORDER_PLACED);
        order.setDate(ZonedDateTime.now());
        order.setTotalCost(totalCost);
        log.info("Order Placed" + order);
        orderRepository.save(order);
        cartRepository.delete(optionalCart.get());
    }

    public void changeOrderStatus(Integer orderId, String status) {
     Optional<Order> optionalOrder = orderRepository.findById(orderId);
     if(!optionalOrder.isPresent()){
         throw new OrderNotFoundException();
     }
     Order order = optionalOrder.get();
        try {
            OrderStatus newStatus = OrderStatus.valueOf(status.toUpperCase());
           // if (!order.getStatus().equals(newStatus)) {
                order.setStatus(newStatus);
                orderRepository.save(order);
           // }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid status: " + status);
        }
    }
}
