package com.example.ecommerceshop.cart.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
public class ProductDto {
    private Integer productId;
    private String productName;
    private Integer productQty;
}