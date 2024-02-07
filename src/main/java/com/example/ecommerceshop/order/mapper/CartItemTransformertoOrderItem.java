package com.example.ecommerceshop.order.mapper;

import com.example.ecommerceshop.cart.model.CartItem;
import com.example.ecommerceshop.order.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartItemTransformertoOrderItem {
    @Mapping(target = "orderItemCost",source="cartItem.cartItemCost")
    @Mapping(target = "id", ignore = true)
    OrderItem toOrderItem(final CartItem cartItem);

    List<OrderItem> toOrderItemList(final List<CartItem> cartItemList);
}
