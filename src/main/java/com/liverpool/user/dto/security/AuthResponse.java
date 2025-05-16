package com.liverpool.user.dto.security;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Response object containing JWT tokens and their expiration times")
public class AuthResponse {

  @Schema(description = "Access token (JWT)", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
  private String accessToken;

  @Schema(description = "Refresh token (JWT)", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
  private String refreshToken;

  @Schema(description = "Expiration timestamp of access token (epoch ms)", example = "1712345678901")
  private long accessTokenExpiresAt;

  @Schema(description = "Expiration timestamp of refresh token (epoch ms)", example = "1719876543210")
  private long refreshTokenExpiresAt;

}