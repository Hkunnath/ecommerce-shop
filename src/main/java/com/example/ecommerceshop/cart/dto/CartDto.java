package com.example.ecommerceshop.cart.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Builder
public class CartDto {
    private String userName;
    private String password;
    private List<ProductDto> productList;
}
