package com.epam.esm.service;

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
     * Find all entities method
     *
     * @return {@link java.util.Collection} of entities
     */
    Collection<T> findAll();

    /**
     * Find all entities by another entity id method
     *
     * @param id another entity id
     * @return {@link java.util.Collection} of entities
     */
    Collection<T> findAll(K id);

    /**
     * Find entity by id method
     *
     * @param id id of entity
     * @return entity or null if entity doesn't exist
     */
    T find(K id);

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
     * @param t  entity for update
     * @return count of updated rows
     * @throws ValidatorException if entity is invalid
     */
    int update(K id, T t) throws ValidatorException;

    /**
     * Delete entity by id method
     *
     * @param id id of entity for delete
     * @return true if entity deleted, false in another way
     */
    boolean delete(K id);
}
