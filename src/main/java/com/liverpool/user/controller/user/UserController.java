package com.liverpool.user.controller.user;

import com.liverpool.user.dto.user.SaveUserRequest;
import com.liverpool.user.dto.user.UserInfo;
import com.liverpool.user.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST que expone las operaciones CRUD de usuarios.
 *
 * @author arturhc
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "UserController", description = "Endpoints para operaciones CRUD de usuarios")
public class UserController {

  private final UserService userService;

  @Operation(
      summary = "Crear usuario",
      description = "Registra un nuevo usuario y devuelve el usuario creado con su ID generado"
  )
  @ApiResponse(responseCode = "200", description = "Usuario creado exitosamente")
  @PostMapping
  public ResponseEntity<UserInfo> create(
      @Validated @RequestBody SaveUserRequest createUserRequest
  ) {
    return ResponseEntity.ok(userService.createUser(createUserRequest));
  }

  @Operation(
      summary = "Obtener usuario por ID",
      description = "Recupera un usuario por su ObjectId de MongoDB"
  )
  @ApiResponse(responseCode = "200", description = "Usuario encontrado")
  @GetMapping("/{id}")
  public UserInfo getUser(@PathVariable String id) {
    return userService.getUser(id);
  }

  @Operation(
      summary = "Listar usuarios",
      description = "Devuelve todos los usuarios registrados en el sistema"
  )
  @ApiResponse(responseCode = "200", description = "Operación exitosa")
  @GetMapping
  public List<UserInfo> getUsers() {
    return userService.getUsers();
  }

  @Operation(
      summary = "Actualizar usuario",
      description = "Actualiza un usuario existente identificado por su ID"
  )
  @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente")
  @PutMapping("/{id}")
  public UserInfo updateUser(
      @PathVariable String id,
      @Validated @RequestBody SaveUserRequest user
  ) {
    return userService.updateUser(id, user);
  }

  @Operation(
      summary = "Eliminar usuario",
      description = "Elimina un usuario dado su ID"
  )
  @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente")
  @DeleteMapping("/{id}")
  public void deleteUser(@PathVariable String id) {
    userService.deleteUser(id);
  }
}
