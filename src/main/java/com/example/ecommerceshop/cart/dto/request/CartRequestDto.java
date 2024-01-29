package com.example.ecommerceshop.cart.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CartRequestDto {
    private List<ProductDto> productList;
}
