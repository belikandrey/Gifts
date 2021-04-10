package com.epam.esm.dao;

import com.epam.esm.dao.criteria.SearchCriteria;
import com.epam.esm.entity.Entity;
import com.epam.esm.exception.EntityAlreadyExistException;
import com.epam.esm.exception.EntityNotFoundException;

import java.util.Collection;

/**
 * Base DAO interface
 *
 * @param <T> class that extends {@link com.epam.esm.entity.Entity}
 * @param <K> transfer object of the T
 * @author Andrey Belik
 * @version 1.0
 */
public interface AbstractDAO<T extends Entity, K> {

  /**
   * Find entity by id method
   *
   * @param id id of the entity
   * @return entity or null if entity doesn't exists
   */
  T findById(K id) throws EntityNotFoundException;

  /**
   * Add entity method
   *
   * @param t entity to add
   * @return count of added rows
   */
  T add(T t) throws EntityAlreadyExistException;

  /**
   * Update entity method
   *
   * @param id id of the entity to update
   * @param t entity to update
   * @return count of updated rows
   */
  void update(K id, T t) throws EntityNotFoundException;

  /**
   * Delete entity method
   *
   * @param id id of the entity to delete
   * @return count of deleted rows
   */
  void delete(K id) throws EntityNotFoundException;
}
