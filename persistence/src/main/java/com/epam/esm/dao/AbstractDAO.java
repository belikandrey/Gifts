package com.epam.esm.dao;

import java.util.Optional;

/**
 * Base DAO interface
 *
 * @param <T> class that extends {@link com.epam.esm.entity}
 * @param <K> transfer object of the T
 * @author Andrey Belik
 * @version 1.0
 */
public interface AbstractDAO<T, K> {

  /**
   * Find entity by id method
   *
   * @param id id of the entity
   * @return {@link Optional} of entity
   */
  Optional<T> findById(K id);

  /**
   * Add entity method
   *
   * @param t entity to add
   * @return added entity
   */
  T add(T t);

  /**
   * Update entity method
   *
   * @param id id of the entity to update
   * @param t entity to update
   * @return true if updated, false in another way
   */
  boolean update(K id, T t);

  /**
   * Delete entity method
   *
   * @param id id of the entity to delete
   * @return true if deleted, false in another way
   */
  boolean delete(K id);
}
