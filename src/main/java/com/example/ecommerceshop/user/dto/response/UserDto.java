package com.example.ecommerceshop.user.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
@Builder
public class UserDto {
  private final String username;
  private final Integer userId;
  private final String jwt;
}
