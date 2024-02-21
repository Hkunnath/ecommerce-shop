package com.example.ecommerceshop.cart.mapper;

import com.example.ecommerceshop.cart.dto.response.CartItemDto;
import com.example.ecommerceshop.cart.model.CartItem;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemTransformer {

  CartItemDto toDto(final CartItem cartItem);

  List<CartItemDto> toDtoList(final List<CartItem> cartItems);
}
