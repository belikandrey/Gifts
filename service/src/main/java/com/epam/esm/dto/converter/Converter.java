package com.epam.esm.dto.converter;

/**
 * Base converter for entities
 *
 * @param <T> type of entity, which extends {@link com.epam.esm.entity}
 * @param <K> type of entity DTO
 * @author Andrey Belik
 * @version 1.0
 */
public interface Converter<T, K> {
  /**
   * Convert DTO entity to entity
   *
   * @param dto dto entity
   * @return entity
   */
  T convertToEntity(K dto);

  /**
   * Convert entity to dto entity
   *
   * @param entity entity
   * @return entity dto
   */
  K convertToDto(T entity);
}
