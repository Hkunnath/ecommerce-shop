package com.example.ecommerceshop.user.auth;

import com.example.ecommerceshop.user.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtUtil {
  private long accessTokenValidity = 60 * 7;
  private final JwtParser jwtParser;

  public static final String TOKEN_HEADER = "Authorization";
  private final String TOKEN_PREFIX = "Bearer ";

  private final SecretKey key;

  public JwtUtil(@Value("${jwt.secret}") final String secretKey) {
    this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    this.jwtParser = Jwts.parser().verifyWith(key).build();
  }

  public String createToken(User user) {
    Claims claims = Jwts.claims().subject(user.getUsername()).build();
    Date tokenCreateTime = new Date();
    Date tokenValidity =
        new Date(tokenCreateTime.getTime() + TimeUnit.MINUTES.toMillis(accessTokenValidity));
    log.info("JWT created");
    return Jwts.builder()
        .claims(claims)
        .claim("user_id", user.getId())
        .claim("email", user.getEmail())
        .claim("role", user.getRole())
        .expiration(tokenValidity)
        .signWith(key)
        .compact();
  }

  public Integer getUserIdFromToken(String token) {
    return (Integer) parseJwtClaims(token).get("user_id");
  }

  public Claims resolveClaims(HttpServletRequest req) {
    try {
      String token = resolveToken(req);
      if (token != null) {
        return parseJwtClaims(token);
      }
      return null;
    } catch (ExpiredJwtException ex) {
      req.setAttribute("expired", ex.getMessage());
      throw ex;
    } catch (Exception ex) {
      req.setAttribute("invalid", ex.getMessage());
      throw ex;
    }
  }

  public String resolveToken(HttpServletRequest request) {

    String bearerToken = request.getHeader(TOKEN_HEADER);
    return getToken(bearerToken);
  }

  public String getToken(final String token) {
    if (token != null && token.startsWith(TOKEN_PREFIX)) {
      return token.substring(TOKEN_PREFIX.length());
    }
    return null;
  }

  public boolean validateClaims(Claims claims) throws AuthenticationException {
    try {
      if (claims == null || claims.getExpiration() == null) {
        throw new IllegalArgumentException("Invalid claims or expiration");
      }
      return claims.getExpiration().after(new Date());
    } catch (ExpiredJwtException e) {
      log.error("Expired JWT Exception");
      throw e;
    } catch (Exception e) {
      log.error("Authentication exception");
      throw e;
    }
  }

  private Claims parseJwtClaims(String token) {
    return jwtParser.parseSignedClaims(token).getPayload();
  }
}
