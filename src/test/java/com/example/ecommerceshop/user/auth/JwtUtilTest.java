package com.example.ecommerceshop.user.auth;

import com.example.ecommerceshop.user.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class JwtUtilTest {

    @Mock
    private User mockUser;

    @Autowired
    private JwtUtil jwtUtil;
    private SecretKey secretKey;

    @BeforeEach
    void setUp(){
        secretKey = Keys.hmacShaKeyFor("MY=SUPER=SECRET=CODE123=333====2323=123==452345=6756=76=".getBytes());
    }

    @Test
    void test_accessToken_uses_Username(){
        when(mockUser.getUsername()).thenReturn("testUser");
        when(mockUser.getEmail()).thenReturn("test@example.com");
        when(mockUser.getRole()).thenReturn("user");

        Claims claims = Jwts.claims().subject(mockUser.getUsername()).build();
        Date tokenCreateTime = new Date();
        long accessTokenValidity = 60 ;

        Date tokenValidity = new Date(tokenCreateTime.getTime() + TimeUnit.MINUTES.toMillis(accessTokenValidity));

        String token = jwtUtil.createToken(mockUser);
        String returnString = Jwts.builder()
                .claims(claims)
                .claim("email", mockUser.getEmail())
                .claim("role", mockUser.getRole())
                .expiration(tokenValidity).signWith(secretKey)
                .compact();

        assertEquals(token,returnString);
    }
}