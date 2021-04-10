package com.epam.esm.service;

import com.epam.esm.exception.EntityAlreadyExistException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.ValidatorException;

import java.util.Collection;

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
   * @return entity or null if entity doesn't exist
   */
  T findById(K id) throws EntityNotFoundException;

  /**
   * Add entity method
   *
   * @param t entity for add
   * @return added entity
   * @throws ValidatorException if entity is invalid
   */
  T add(T t) throws ValidatorException, EntityAlreadyExistException;

  /**
   * Update entity method
   *
   * @param id id of entity for update
   * @param t entity for update
   * @return count of updated rows
   * @throws ValidatorException if entity is invalid
   */
  void update(K id, T t) throws ValidatorException, EntityNotFoundException;

  /**
   * Delete entity by id method
   *
   * @param id id of entity for delete
   * @return true if entity deleted, false in another way
   */
  void delete(K id) throws EntityNotFoundException;
}
