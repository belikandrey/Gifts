package com.epam.esm.dto.converter;

import com.epam.esm.entity.Entity;

/**
 * Base converter for entities
 *
 * @param <T> type of entity, which extends {@link com.epam.esm.entity.Entity}
 * @param <K> type of entity DTO
 * @author Andrey Belik
 * @version 1.0
 */
public interface Converter<T extends Entity, K> {
  /**
   * Convert DTO entity to entity
   *
   * @param dto dto entity
   * @return entity
   */
  T convert(K dto);

  /**
   * Convert entity to dto entity
   *
   * @param entity entity
   * @return entity dto
   */
  K convert(T entity);
}
