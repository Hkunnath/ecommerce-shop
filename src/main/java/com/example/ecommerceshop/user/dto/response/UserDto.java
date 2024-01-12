package com.example.ecommerceshop.user.dto.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class UserDto {
    private final String username;
    private final Integer userId;
    private final String jwt;
}
