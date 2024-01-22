package com.example.ecommerceshop.user.auth;

import com.example.ecommerceshop.user.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtUtilTest {

    @Mock
    private User mockUser;
    private Key mockKey;

    @InjectMocks
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp(){
        String secretKey = "testSecretKey";
        mockKey = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    @Test
    void test_accessToken_uses_Username(){
        when(mockUser.getUsername()).thenReturn("testUser");
        when(mockUser.getEmail()).thenReturn("test@example.com");
        when(mockUser.getRole()).thenReturn("user");

        Claims claims = Jwts.claims().subject(mockUser.getUsername()).build();
        Date tokenCreateTime = new Date();
        long accessTokenValidity = 60 * 60 * 1000;

        Date tokenValidity = new Date(tokenCreateTime.getTime() + TimeUnit.MINUTES.toMillis(accessTokenValidity));

        String token = jwtUtil.createToken(mockUser);
        String returnString = Jwts.builder()
                .claims(claims)
                .claim("email", mockUser.getEmail())
                .claim("role", mockUser.getRole())
                .expiration(tokenValidity).signWith(mockKey)
                .compact();

        assertEquals(token,returnString);
    }

}