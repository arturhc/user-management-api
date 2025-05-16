package com.liverpool.user.service.user;


import com.liverpool.user.dto.user.SaveUserRequest;
import com.liverpool.user.dto.user.UserInfo;

import java.util.List;

/**
 * Servicio encargado de registrar usuarios en el sistema.
 * <p>
 * 1. Valida datos de entrada y unicidad del correo. <br>
 * 2. Consulta la API de COPOMEX con el código postal para completar la dirección física. <br>
 * 3. Persiste el usuario en la base de datos. <br>
 * </p>
 */
public interface UserService {

  /**
   * Registra un nuevo usuario enriqueciendo su dirección mediante la API de COPOMEX.
   *
   * @param request datos necesarios para crear el usuario (nombre, apellidos, correo y CP)
   * @return representación del usuario ya almacenado
   */
  UserInfo createUser(SaveUserRequest request);

  UserInfo getUser(String userId);

  List<UserInfo> getUsers();

  UserInfo updateUser(String userId, SaveUserRequest user);

  void deleteUser(String userId);

}
