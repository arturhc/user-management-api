package com.liverpool.user.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoZipCodeDataFound extends RuntimeException {

  public NoZipCodeDataFound(String zipCode) {
    super(String.format("El zip code: %s no tiene datos", zipCode));
  }

}
