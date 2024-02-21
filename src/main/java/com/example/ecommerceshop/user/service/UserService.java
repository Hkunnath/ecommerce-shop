package com.example.ecommerceshop.user.service;

import com.example.ecommerceshop.user.auth.JwtUtil;
import com.example.ecommerceshop.user.dto.request.LoginRequest;
import com.example.ecommerceshop.user.dto.request.SignupRequest;
import com.example.ecommerceshop.user.dto.response.UserDto;
import com.example.ecommerceshop.user.exception.UserAlreadyExistException;
import com.example.ecommerceshop.user.exception.UserNotFoundException;
import com.example.ecommerceshop.user.model.User;
import com.example.ecommerceshop.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
  private final UserRepository userRepository;
  private final JwtUtil jwtUtil;
  private final PasswordEncoder passwordEncoder;

  public UserDto registerUser(SignupRequest signUpRequest) {
    User user =
        new User(
            signUpRequest.getUsername(),
            signUpRequest.getEmail(),
            passwordEncoder.encode(signUpRequest.getPassword()),
            signUpRequest.getRole().toUpperCase());
    try {
      userRepository.save(user);
    } catch (DataIntegrityViolationException e) {
      log.error("Exception ::: ", e);
      throw new UserAlreadyExistException();
    }
    log.info("User Registered successfully");
    final String token = jwtUtil.createToken(user);
    return new UserDto(user.getUsername(), user.getId(), token);
  }

  public UserDto loginUser(LoginRequest loginRequest) {
    Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());
    if (!user.isPresent()) {
      log.error("User not found");
      throw new UserNotFoundException("User not found");
    }
    if (!passwordEncoder.matches(loginRequest.getPassword(), user.get().getPassword())) {
      log.error("Password is incorrect");
      throw new UserNotFoundException("Incorrect password");
    }
    final String token = jwtUtil.createToken(user.get());
    return new UserDto(user.get().getUsername(), user.get().getId(), token);
  }
}
