package com.liverpool.user.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(name = "Address", description = "Detalles de la dirección física del usuario")
public class AddressInfo {

  @Schema(description = "Calle y número", example = "Av. Central 123")
  private String street;

  @Schema(description = "Asentamiento o colonia", example = "Centro")
  private String neighborhood;

  @Schema(description = "Estado", example = "Chiapas")
  private String state;

  @Schema(description = "Ciudad", example = "Tuxtla Gutiérrez")
  private String city;

  @Schema(description = "Código postal", example = "29000")
  private String zipCode;

  @Schema(description = "Número exterior", example = "123")
  private String exteriorNumber;

}