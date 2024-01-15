package com.example.ecommerceshop.user.controller;

import com.example.ecommerceshop.user.dto.request.LoginRequest;
import com.example.ecommerceshop.user.dto.request.SignupRequest;
import com.example.ecommerceshop.user.dto.response.UserDto;
import com.example.ecommerceshop.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Mock
    UserService mockuserService;
    @InjectMocks
    UserController userController;
    @Test
    void ShouldCreateSuccessfully(){
        final String userName = "testName";
        final String email = "test@gmail.com";
        final String role = "testRole";
        UserDto expected = new UserDto(userName, 1, "jwt");
        final SignupRequest signupRequest = SignupRequest.builder().username(userName).build();
        when(mockuserService.registerUser(signupRequest)).thenReturn(expected);
        final UserDto userDto = userController.registerUser(signupRequest);
        assertEquals(expected, userDto);
    }
    @Test
    void ShouldLoginSuccesfully(){
        final String userName = "testName";
        final String password = "password";
        UserDto expected = new UserDto(userName,1,"jwt");
        final LoginRequest loginRequest = LoginRequest.builder().username(userName).build();
        when(mockuserService.loginUser(loginRequest)).thenReturn(expected);
        final UserDto userDto = userController.loginUser(loginRequest);
        assertEquals(expected,userDto);
    }
}