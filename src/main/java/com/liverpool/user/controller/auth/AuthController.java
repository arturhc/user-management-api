package com.liverpool.user.controller.auth;

import com.liverpool.user.dto.security.AuthRequest;
import com.liverpool.user.dto.security.AuthResponse;
import com.liverpool.user.service.security.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST encargado de la autenticación de usuarios y la
 * generación/renovación de tokens JWT.
 *
 * @author arturhc
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "AuthController", description = "Endpoints para iniciar sesión y refrescar tokens JWT")
public class AuthController {

  private final AuthService authService;

  @Operation(
      summary = "Autenticar usuario",
      description = "Inicia sesión de un usuario y devuelve los tokens de acceso y refresco"
  )
  @ApiResponse(responseCode = "200", description = "Autenticación exitosa")
  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
    return ResponseEntity.ok(authService.authenticate(request));
  }

  @Operation(
      summary = "Refrescar token JWT",
      description = "Utiliza el token de refresco para generar un nuevo token de acceso"
  )
  @ApiResponse(responseCode = "200", description = "Nuevo token de acceso generado")
  @PostMapping("/refresh")
  public ResponseEntity<AuthResponse> refresh(@Valid @RequestBody String refreshToken) {
    return ResponseEntity.ok(authService.refresh(refreshToken));
  }

}
