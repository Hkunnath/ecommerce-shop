package com.example.ecommerceshop.product.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@Data
public class ProductDto {
    private Integer productId;
    private String productName;

    public ProductDto(Integer productId, String productName) {
        this.productId = productId;
        this.productName = productName;
    }
}
