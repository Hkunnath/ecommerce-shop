package com.example.ecommerceshop.order.mapper;

import com.example.ecommerceshop.cart.model.CartItem;
import com.example.ecommerceshop.order.model.OrderItem;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemTransformertoOrderItem {
  @Mapping(target = "orderItemCost", source = "cartItem.cartItemCost")
  @Mapping(target = "id", ignore = true)
  OrderItem toOrderItem(final CartItem cartItem);

  List<OrderItem> toOrderItemList(final List<CartItem> cartItemList);
}
