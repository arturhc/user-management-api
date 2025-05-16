package com.liverpool.user.service.user;

import com.liverpool.user.dto.external.CopomexApiResponse;
import com.liverpool.user.dto.user.SaveUserRequest;
import com.liverpool.user.dto.user.UserInfo;
import com.liverpool.user.exception.user.DuplicatedUserException;
import com.liverpool.user.exception.user.NoZipCodeDataFound;
import com.liverpool.user.exception.user.UserNotFoundException;
import com.liverpool.user.model.Address;
import com.liverpool.user.model.User;
import com.liverpool.user.repository.UserMongoRepository;
import com.liverpool.user.service.external.SepomexRestClient;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación de {@link UserService} que gestiona las operaciones CRUD
 * de usuarios en MongoDB y aplica caché para mejorar el rendimiento.
 */
@Service
@RequiredArgsConstructor
public class MongoUserServiceImpl implements UserService {

  /**
   * Repositorio para acceder y persistir usuarios
   */
  private final UserMongoRepository userRepository;

  /**
   * Cliente REST para obtener información de direcciones vía SEPOMEX
   */
  private final SepomexRestClient sepomexRestClient;

  /**
   * Utilidad para mapear entre entidades y DTOs
   */
  private final ModelMapper mapper;

  /**
   * Crea un nuevo usuario y limpia la caché de la lista de usuarios.
   */
  @Override
  @Transactional
  @Caching(evict = {
      @CacheEvict(value = "all_users", allEntries = true)
  })
  public UserInfo createUser(SaveUserRequest request) {

    final String zipCode = request.getZipCode();

    if (userRepository.existsByEmail(request.getEmail())) {
      throw new DuplicatedUserException(request.getEmail());
    }

    CopomexApiResponse.AddressResponse addressResponse = sepomexRestClient.findByZip(zipCode);

    if (addressResponse == null) {
      throw new NoZipCodeDataFound(zipCode);
    }

    Address address = Address.builder()
        .city(addressResponse.getCiudad())
        .state(addressResponse.getEstado())
        .zipCode(request.getZipCode())
        .street(request.getStreet())
        .neighborhood(request.getNeighborhood())
        .exteriorNumber(request.getExteriorNumber())
        .build();

    User user = User.builder()
        .name(request.getName())
        .lastName(request.getLastName())
        .secondLastName(request.getSecondLastName())
        .email(request.getEmail())
        .address(address)
        .build();

    userRepository.save(user);

    return mapper.map(user, UserInfo.class);
  }

  /**
   * Obtiene un usuario por su ID. Si existe en caché se retorna desde allí.
   */
  @Override
  @Cacheable(value = "users", key = "#userId")
  public UserInfo getUser(String userId) {

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));

    return mapper.map(user, UserInfo.class);
  }

  /**
   * Recupera la lista completa de usuarios. El resultado se almacena en caché.
   */
  @Override
  @Cacheable(value = "all_users")
  public List<UserInfo> getUsers() {
    return userRepository.findAll()
        .stream()
        .map(user -> mapper.map(user, UserInfo.class))
        .toList();
  }

  /**
   * Actualiza un usuario y limpia la caché del usuario individual y de la lista.
   */
  @Override
  @Transactional
  @Caching(evict = {
      @CacheEvict(value = "users", key = "#id"),
      @CacheEvict(value = "all_users", allEntries = true)
  })
  public UserInfo updateUser(String id, SaveUserRequest user) {

    User userToUpdate = userRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

    mapper.map(user, userToUpdate);

    mapper.map(user, userToUpdate.getAddress());

    userRepository.save(userToUpdate);

    return mapper.map(userToUpdate, UserInfo.class);
  }

  /**
   * Elimina un usuario y limpia la caché correspondiente.
   */
  @Override
  @Transactional
  @Caching(evict = {
      @CacheEvict(value = "users", key = "#id"),
      @CacheEvict(value = "all_users", allEntries = true)
  })
  public void deleteUser(String id) {

    User user = userRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

    userRepository.delete(user);
  }

}