package com.liverpool.user.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicatedUserException extends RuntimeException {

  public DuplicatedUserException(String email) {
    super(String.format("El usuario con email: %s ya existe", email));
  }

}
