package com.example.ecommerceshop.user.controller;

import com.example.ecommerceshop.user.dto.request.SignupRequest;
import com.example.ecommerceshop.user.dto.response.UserDto;
import com.example.ecommerceshop.user.service.UserService;
import jakarta.validation.Valid;
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
    public UserDto registerUser(@Valid @RequestBody SignupRequest signUpRequest){
        System.out.println("Here I'm");
        return userService.registerUser(signUpRequest);
    }

    @GetMapping("/login")
    public ResponseEntity<Map<String, String>> test(){
        System.out.println("Here I'm in login");
        return ResponseEntity.ok(Map.of("Message", "Success"));
    }

}
