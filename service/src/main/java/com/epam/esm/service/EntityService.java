package com.epam.esm.service;

import com.epam.esm.dto.UserDTO;
import com.epam.esm.exception.ValidatorException;

/**
 * Base service interface
 *
 * @param <T> entity for service
 * @param <K> type of entity id
 */
public interface EntityService<T, K> {

  /**
   * Find entity by id method
   *
   * @param id id of entity
   * @return entity
   */
  T findById(K id);

  /**
   * Add entity method
   *
   * @param t entity for add
   * @return added entity
   * @throws ValidatorException if entity is invalid
   */
  T add(T t) throws ValidatorException;

  /**
   * Update entity method
   *
   * @param id id of entity for update
   * @param t entity for update
   * @throws ValidatorException if entity is invalid
   */
  /**
   * Delete entity by id method
   *
   * @param id id of entity for delete
   */
  void delete(K id);
}
