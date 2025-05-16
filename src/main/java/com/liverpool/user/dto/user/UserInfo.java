package com.liverpool.user.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Respuesta devuelta después de registrar un usuario satisfactoriamente o al obtener la información de un usuario.
 *
 * @author ac40756
 */
@Data
@NoArgsConstructor
@Schema(name = "UserInfo", description = "Respuesta devuelta tras crear un usuario u obtener usuarios")
public class UserInfo {

  @Schema(description = "Identificador del usuario", example = "6646391b41a2f5563a89d912")
  private String id;

  @Schema(description = "Nombre del usuario", example = "Arturo")
  private String name;

  @Schema(description = "Apellido paterno", example = "Cordero")
  private String lastName;

  @Schema(description = "Apellido materno", example = "Muñiz")
  private String secondLastName;

  @Schema(description = "Correo electrónico", example = "arturo@example.com")
  private String email;

  @Schema(description = "Dirección física del usuario")
  private AddressInfo address;

}
