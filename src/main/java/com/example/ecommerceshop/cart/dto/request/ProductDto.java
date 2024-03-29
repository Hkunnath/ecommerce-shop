package com.example.ecommerceshop.cart.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {
  private Integer productId;
  private Integer productQty;
  private Double totalCost;
}
