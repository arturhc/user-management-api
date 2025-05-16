package com.liverpool.user.service.external;

import com.liverpool.user.dto.external.CopomexApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SepomexRestClient {

  private final RestTemplate restTemplate;

  @Value("${copomex.token}")
  private String apiToken;

  private static final String URL_TEMPLATE =
      "https://api.copomex.com/query/info_cp/%s?token=%s&type=simplified";

  /**
   * Consulta la API de COPOMEX y regresa la lista de colonias / asentamientos para el CP.
   */
  public CopomexApiResponse.AddressResponse findByZip(String zip) {

    String url = String.format(URL_TEMPLATE, zip, apiToken);

    ResponseEntity<CopomexApiResponse> response = restTemplate.exchange(
        url, HttpMethod.GET, null, CopomexApiResponse.class
    );

    CopomexApiResponse body = response.getBody();

    CopomexApiResponse.AddressResponse addressResponse = body.getResponse();

    return addressResponse;
  }
}