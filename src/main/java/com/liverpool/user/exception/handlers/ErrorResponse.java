package com.liverpool.user.exception.handlers;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Representa una respuesta de error estructurada devuelta por la API.
 */
@Data
@AllArgsConstructor
@Schema(description = "Respuesta de error estructurada devuelta por la API")
public class ErrorResponse {

  @Schema(description = "Código de estado HTTP del error", example = "400")
  private int status;

  @Schema(description = "Nombre del error (p. ej., Bad Request, Not Found)", example = "Bad Request")
  private String error;

  @Schema(description = "Mensaje de error detallado", example = "Valor inválido para enum OrderStatusType")
  private String message;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
  @Schema(description = "Marca temporal en la que ocurrió el error", example = "2025-03-22T14:25:00")
  private LocalDateTime timestamp;

}
