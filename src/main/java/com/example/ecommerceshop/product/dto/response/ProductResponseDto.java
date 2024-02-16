package com.example.ecommerceshop.product.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductResponseDto {
  private Integer productId;
  private String productName;
  private Double productPrice;
  private Integer stockQuantity;
  private String productDescription;
}
