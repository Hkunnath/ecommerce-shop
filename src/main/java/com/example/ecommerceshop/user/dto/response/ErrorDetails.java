package com.example.ecommerceshop.user.dto.response;

import lombok.Builder;

@Builder
public record ErrorDetails(String message, String details, int httpStatusCode) {}
