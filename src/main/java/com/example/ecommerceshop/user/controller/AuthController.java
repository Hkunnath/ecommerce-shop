package com.example.ecommerceshop.user.controller;

import com.example.ecommerceshop.user.dto.request.LoginRequest;
import com.example.ecommerceshop.user.dto.request.SignupRequest;
import com.example.ecommerceshop.user.dto.response.UserDto;
import com.example.ecommerceshop.user.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")

public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public UserDto registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        return userService.registerUser(signUpRequest);
    }

    @PostMapping("/login")
    public UserDto loginUser(@Valid @RequestBody LoginRequest loginRequest){
        return userService.loginUser(loginRequest);
    }
}
