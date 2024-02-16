package com.example.ecommerceshop.order.dto;

import com.example.ecommerceshop.order.model.OrderStatus;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDto {
  private OrderStatus status;
  private double totalCost;
  private LocalDateTime date;
  private Integer userId;
}
