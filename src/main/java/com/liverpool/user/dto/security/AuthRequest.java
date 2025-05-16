package com.liverpool.user.dto.security;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Request object containing user credentials")
public class AuthRequest {

  @NotBlank(message = "Username is required")
  @Schema(description = "Username of the user", example = "admin", requiredMode = Schema.RequiredMode.REQUIRED)
  private String username;

  @NotBlank(message = "Password is required")
  @Schema(description = "Password of the user", example = "admin123", requiredMode = Schema.RequiredMode.REQUIRED)
  private String password;

}