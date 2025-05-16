package com.liverpool.user.service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtService {

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.access-token-expiration}")
  private long accessTokenExpiration;

  @Value("${jwt.refresh-token-expiration}")
  private long refreshTokenExpiration;

  private Key key;

  @PostConstruct
  public void init() {
    byte[] decodedKey = Base64.getDecoder().decode(secret);
    this.key = Keys.hmacShaKeyFor(decodedKey);
  }

  public String generateAccessToken(UserDetails userDetails) {

    return Jwts.builder()
        .setSubject(userDetails.getUsername())
        .claim("roles", extractRoles(userDetails.getAuthorities()))
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

  public String generateRefreshToken(UserDetails userDetails) {

    return Jwts.builder()
        .setSubject(userDetails.getUsername())
        .claim("roles", extractRoles(userDetails.getAuthorities()))
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

  public String extractUsername(String token) {

    return Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public boolean isTokenValid(String token, String username) {
    return extractUsername(token).equals(username);
  }

  public Date getAccessTokenExpiration(String token) {
    return getExpirationDate(token);
  }

  public Date getRefreshTokenExpiration(String token) {
    return getExpirationDate(token);
  }

  private Date getExpirationDate(String token) {
    return parseAllClaims(token).getExpiration();
  }

  private Claims parseAllClaims(String token) {

    return Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private String extractRoles(Collection<? extends GrantedAuthority> authorities) {

    return authorities.stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(","));
  }

  public Collection<? extends GrantedAuthority> extractRoles(String token) {

    Claims claims = Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody();

    String rolesString = claims.get("roles", String.class);
    List<String> roles = List.of(rolesString.split(","));

    return roles.stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }
}
