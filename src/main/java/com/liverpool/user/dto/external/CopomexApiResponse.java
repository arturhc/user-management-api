package com.liverpool.user.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CopomexApiResponse {

  private boolean error;

  @JsonProperty("code_error")
  private int codeError;

  @JsonProperty("error_message")
  private String errorMessage;

  private AddressResponse response;

  @Data
  public static class AddressResponse {

    private String cp;
    private List<String> asentamiento;

    @JsonProperty("tipo_asentamiento")
    private String tipoAsentamiento;

    private String municipio;
    private String estado;
    private String ciudad;
    private String pais;
  }

}
