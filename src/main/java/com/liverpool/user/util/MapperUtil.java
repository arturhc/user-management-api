package com.liverpool.user.util;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MapperUtil {

  private final ModelMapper modelMapper;

  public MapperUtil(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  /**
   * Convierte un objeto fuente en un objeto destino especificado por la clase.
   */
  public <S, T> T map(S source, Class<T> targetClass) {
    return modelMapper.map(source, targetClass);
  }

  /**
   * Convierte una lista de objetos fuente en una lista de objetos destino.
   */
  public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
    return source.stream()
        .map(element -> modelMapper.map(element, targetClass))
        .collect(Collectors.toList());
  }

  /**
   * Mapea los datos del objeto fuente a un objeto destino existente.
   */
  public <S, T> void mapToExistingObject(S source, T destination) {
    modelMapper.map(source, destination);
  }
}
