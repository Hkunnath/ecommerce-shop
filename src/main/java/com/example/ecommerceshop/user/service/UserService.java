package com.example.ecommerceshop.user.service;

import com.example.ecommerceshop.user.auth.JwtUtil;
import com.example.ecommerceshop.user.dto.response.UserDto;
import com.example.ecommerceshop.user.model.User;
import com.example.ecommerceshop.user.dto.request.SignupRequest;
import com.example.ecommerceshop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public UserDto registerUser(SignupRequest signUpRequest) {
        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPassword(), signUpRequest.getRole());
        userRepository.save(user);
        final String token = jwtUtil.createToken(user);
        return new UserDto(user.getUsername(), user.getId(), token);
    }
}
