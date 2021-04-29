package com.epam.esm.service;

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
}
