package com.example.ecommerceshop.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.example.ecommerceshop.user.auth.JwtUtil;
import com.example.ecommerceshop.user.dto.request.LoginRequest;
import com.example.ecommerceshop.user.dto.request.SignupRequest;
import com.example.ecommerceshop.user.dto.response.UserDto;
import com.example.ecommerceshop.user.exception.UserNotFoundException;
import com.example.ecommerceshop.user.model.User;
import com.example.ecommerceshop.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
  @Mock UserRepository userRepository;
  @Mock JwtUtil jwtUtil;

  @Mock BCryptPasswordEncoder passwordEncoder;
  @InjectMocks UserService userService;
  private String userName;
  private String password;

  @BeforeEach
  void SetUp() {
    userName = "testName";
    password = "password";
  }

  @Test
  void shouldRegisterSuccessfully() {
    final String email = "test@gmail.com";
    final String role = "USER";
    final SignupRequest signupRequest =
        SignupRequest.builder()
            .username(userName)
            .email(email)
            .password(password)
            .role(role)
            .build();
    final String jwt = "jwt";
    when(passwordEncoder.encode(anyString())).thenReturn("password");
    final User user =
        new User(userName, email, passwordEncoder.encode(password), role.toUpperCase());
    when(jwtUtil.createToken(user)).thenReturn(jwt);
    UserDto expected = UserDto.builder().username(userName).jwt(jwt).build();
    UserDto actual = userService.registerUser(signupRequest);
    assertEquals(expected, actual);
  }

  @Test
  void shouldLoginSuccessfully() {
    final String email = "test@gmail.com";
    final String role = "USER";
    final User user = new User(userName, email, password, role);
    final LoginRequest loginRequest =
        LoginRequest.builder().username(userName).password(password).build();
    final String jwt = "jwt";
    UserDto expected = UserDto.builder().username(userName).jwt(jwt).build();
    when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(true);
    when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(Optional.of(user));
    when(jwtUtil.createToken(user)).thenReturn(jwt);
    UserDto actual = userService.loginUser(loginRequest);
    assertEquals(expected, actual);
  }

  @Test
  void whenExceptionThrownWhenUsernameNotFound() {
    final LoginRequest loginRequest =
        LoginRequest.builder().username(userName).password(password).build();
    when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(Optional.empty());

    Exception exception =
        assertThrows(
            UserNotFoundException.class,
            () -> {
              userService.loginUser(loginRequest);
            });

    String expectedMessage = "User not found";
    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void whenExceptionThrownWhenPasswordIncorrect() {
    final String email = "test@gmail.com";
    final String role = "testRole";
    final User user = new User(userName, email, password, role);

    final LoginRequest loginRequest =
        LoginRequest.builder().username(userName).password(password).build();
    when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(Optional.of(user));
    when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(false);

    Exception exception =
        assertThrows(
            UserNotFoundException.class,
            () -> {
              userService.loginUser(loginRequest);
            });

    String expectedMessage = "Incorrect password";
    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);
  }
}
