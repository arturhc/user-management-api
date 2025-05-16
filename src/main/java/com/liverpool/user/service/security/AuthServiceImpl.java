package com.liverpool.user.service.security;

import com.liverpool.user.dto.security.AuthRequest;
import com.liverpool.user.dto.security.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;

  @Override
  public AuthResponse authenticate(AuthRequest request) {
    Authentication auth = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
    );

    UserDetails userDetails = (UserDetails) auth.getPrincipal();
    String accessToken = jwtService.generateAccessToken(userDetails);
    String refreshToken = jwtService.generateRefreshToken(userDetails);

    return new AuthResponse(
        accessToken,
        refreshToken,
        jwtService.getAccessTokenExpiration(accessToken).getTime(),
        jwtService.getRefreshTokenExpiration(refreshToken).getTime()
    );
  }

  @Override
  public AuthResponse refresh(String refreshToken) {
    String username = jwtService.extractUsername(refreshToken);
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

    if (!jwtService.isTokenValid(refreshToken, username)) {
      throw new RuntimeException("Invalid refresh token");
    }

    String newAccessToken = jwtService.generateAccessToken(userDetails);
    return new AuthResponse(
        newAccessToken,
        refreshToken,
        jwtService.getAccessTokenExpiration(newAccessToken).getTime(),
        jwtService.getRefreshTokenExpiration(refreshToken).getTime()
    );
  }

}
