package com.example.ecommerceshop.cart.mapper;

import com.example.ecommerceshop.cart.dto.response.CartItemDto;
import com.example.ecommerceshop.cart.model.CartItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartItemTransformer {

    CartItemDto toDto(final CartItem cartItem);

    List<CartItemDto> toDtoList(final List<CartItem> cartItems);

}
