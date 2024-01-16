package com.example.ecommerceshop.product.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDetailsDto {
    private String productName;
    private String productDescription;
    private double productPrice;
    private Integer stockQuantity;
}
