package com.example.ecommerceshop.order.dto;

import com.example.ecommerceshop.order.model.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OrderDto {
    private OrderStatus status;
    private double totalCost;
    private LocalDateTime date;
    private Integer userId;
}
