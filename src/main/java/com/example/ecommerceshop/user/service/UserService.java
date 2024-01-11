package com.example.ecommerceshop.user.service;

import com.example.ecommerceshop.user.dto.response.UserDto;
import com.example.ecommerceshop.user.model.User;
import com.example.ecommerceshop.user.dto.request.SignupRequest;
import com.example.ecommerceshop.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto registerUser(SignupRequest signUpRequest){
        User user = new User(signUpRequest.getUsername(),signUpRequest.getEmail(),signUpRequest.getPassword(),signUpRequest.getRole());
        userRepository.save(user);
        return new UserDto(user.getUsername(),user.getId());
    }
}
