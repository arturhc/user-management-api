package com.liverpool.user.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Cuerpo de la petición usado para crear o actualizar un usuario.
 *
 * @author arturhc
 */
@Data
@Schema(name = "SaveUserRequest", description = "Payload para crear o actualizar un usuario")
public class SaveUserRequest {

  @NotBlank
  @Schema(description = "Nombre del usuario", example = "Arturo", requiredMode = Schema.RequiredMode.REQUIRED)
  private String name;

  @NotBlank
  @Schema(description = "Apellido paterno", example = "Cordero", requiredMode = Schema.RequiredMode.REQUIRED)
  private String lastName;

  @Schema(description = "Apellido materno", example = "Muñiz")
  private String secondLastName;

  @NotBlank
  @Email
  @Schema(description = "Correo electrónico", example = "arturo@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
  private String email;

  @NotBlank
  @Size(min = 5, max = 5)
  @Schema(description = "Código postal mexicano (5 dígitos)", example = "29000", requiredMode = Schema.RequiredMode.REQUIRED)
  private String zipCode;

  @NotBlank
  @Schema(description = "Calle", example = "Av. Revolución", requiredMode = Schema.RequiredMode.REQUIRED)
  private String street;

  @NotBlank
  @Schema(description = "Número exterior", example = "123", requiredMode = Schema.RequiredMode.REQUIRED)
  private String exteriorNumber;

  @NotBlank
  @Schema(description = "Colonia", example = "Centro", requiredMode = Schema.RequiredMode.REQUIRED)
  private String neighborhood;


}
