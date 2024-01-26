package com.example.ecommerceshop.cart.mapper;
import com.example.ecommerceshop.cart.dto.response.CartDto;
import com.example.ecommerceshop.cart.model.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CartItemTransformer.class})
public interface CartTransformer {
    CartDto toDto(final Cart cart);

    Cart toEntity(final CartDto cartDto);
}
