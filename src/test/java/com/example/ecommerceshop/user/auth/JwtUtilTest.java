package com.example.ecommerceshop.user.auth;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.ecommerceshop.user.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.crypto.SecretKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JwtUtilTest {

  public static final String SECRET_KEY =
      "MY=SUPER=SECRET=CODE123=333====2323=123==452345=6756=76=";
  private User mockUser;

  private JwtUtil jwtUtil;
  private SecretKey secretKey;

  private HttpServletRequest mockRequest;

  private Claims mockClaims;

  @BeforeEach
  void setUp() {
    mockUser = mock(User.class);
    mockRequest = mock(HttpServletRequest.class);
    mockClaims = mock(Claims.class);
    jwtUtil = new JwtUtil(SECRET_KEY);
    secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
  }

  @Test
  void test_accessToken_uses_Username() {
    when(mockUser.getUsername()).thenReturn("testUser");
    when(mockUser.getEmail()).thenReturn("test@example.com");
    when(mockUser.getRole()).thenReturn("USER");
    when(mockUser.getId()).thenReturn(1);

    Claims claims = Jwts.claims().subject(mockUser.getUsername()).build();
    Date tokenCreateTime = new Date();
    long accessTokenValidity = 60 * 7;

    Date tokenValidity =
        new Date(tokenCreateTime.getTime() + TimeUnit.MINUTES.toMillis(accessTokenValidity));

    String token = jwtUtil.createToken(mockUser);
    String returnString =
        Jwts.builder()
            .claims(claims)
            .claim("user_id", mockUser.getId())
            .claim("email", mockUser.getEmail())
            .claim("role", mockUser.getRole().toUpperCase())
            .expiration(tokenValidity)
            .signWith(secretKey)
            .compact();

    assertEquals(token, returnString);
  }

  @Test
  void resolveToken() {
    final String TOKEN_HEADER = "Authorization";
    final String TOKEN_PREFIX = "Bearer ";
    final String validToken = "token";
    when(mockRequest.getHeader(TOKEN_HEADER)).thenReturn(TOKEN_PREFIX + validToken);
    assertEquals(validToken, jwtUtil.resolveToken(mockRequest));
  }

  @Test
  void validClaims() {
    Date futureExpiration = new Date(System.currentTimeMillis() + 60);
    when(mockClaims.getExpiration()).thenReturn(futureExpiration);
    assertTrue(jwtUtil.validateClaims(mockClaims));
  }

  @Test
  void nullClaimsThrowsException() {
    Exception exception =
        assertThrows(IllegalArgumentException.class, () -> jwtUtil.validateClaims(null));
    String expectedMessage = "Invalid claims or expiration";
    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  void nullExpirationThrowsException() {
    when(mockClaims.getExpiration()).thenReturn(null);
    Exception exception =
        assertThrows(IllegalArgumentException.class, () -> jwtUtil.validateClaims(null));
    String expectedMessage = "Invalid claims or expiration";
    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);
  }
}
