package com.example.ecommerceshop.user.service;

import com.example.ecommerceshop.user.model.User;
import com.example.ecommerceshop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            throw new RuntimeException("User not found");
        }

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                        .username(user.get().getEmail())
                        .password(user.get().getPassword())
                        .roles(user.get().getRole())
                        .build();

        return userDetails;
    }
}
