package com.example.ecommerceshop.user.service;

import com.example.ecommerceshop.user.exception.UserNotFoundException;
import com.example.ecommerceshop.user.auth.JwtUtil;
import com.example.ecommerceshop.user.dto.request.LoginRequest;
import com.example.ecommerceshop.user.dto.request.SignupRequest;
import com.example.ecommerceshop.user.dto.response.UserDto;
import com.example.ecommerceshop.user.model.User;
import com.example.ecommerceshop.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    JwtUtil jwtUtil;

    @InjectMocks
    UserService userService;


   @Test
    void shouldRegisterSuccessfully(){

       final String userName = "testName";
       final String password = "password";
       final String email = "test@gmail.com";
       final String role = "testRole";
       final User user = new User(userName,email,password,role);
       final SignupRequest signupRequest = SignupRequest.builder().username(userName).email(email).password(password).role(role).build();
       final String jwt = "jwt";
       when(jwtUtil.createToken(user)).thenReturn(jwt);
       UserDto expected = UserDto.builder().username(userName).jwt(jwt).build();
       UserDto actual = userService.registerUser(signupRequest);
       assertEquals(expected,actual);

   }


   @Test
    void shouldLoginSuccessfully(){
       final String userName = "testName";
       final String password = "password";
       final String email = "test@gmail.com";
       final String role = "testRole";
       final User user = new User(userName,email,password,role);
       final LoginRequest loginRequest = LoginRequest.builder().username(userName).password(password).build();
       final String jwt = "jwt";
       UserDto expected = UserDto.builder().username(userName).jwt(jwt).build();
       when(userRepository.findByUsernameAndPassword(loginRequest.getUsername(),loginRequest.getPassword())).thenReturn(Optional.of(user));
       when(jwtUtil.createToken(user)).thenReturn(jwt);
       UserDto actual = userService.loginUser(loginRequest);
       assertEquals(expected,actual);
   }

   @Test
    void whenExceptionThrownThenAssertionSucceeds() {
       final String userName = "testName";
       final String password = "password";
       final LoginRequest loginRequest = LoginRequest.builder().username(userName).password(password).build();
       when(userRepository.findByUsernameAndPassword(loginRequest.getUsername(),loginRequest.getPassword())).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userService.loginUser(loginRequest);
        });

        String expectedMessage = "User not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}