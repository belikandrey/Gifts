package com.epam.esm.dao;

import com.epam.esm.entity.Entity;

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
     * Find all entities method
     * @return {@link java.util.Collection} of entities
     */
    Collection<T> findAll();

    /**
     * Find all entity by another entity id method
     * @param id another entity id(f.e. tag id when searching certificate)
     * @return {@link java.util.Collection} of entities
     */
    Collection<T> findAll(K id);

    /**
     * Find entity by id method
     * @param id id of the entity
     * @return entity or null if entity doesn't exists
     */
    T find(K id);

    /**
     * Add entity method
     * @param t entity to add
     * @return count of added rows
     */
    int add(T t);

    /**
     * Update entity method
     * @param id id of the entity to update
     * @param t entity to update
     * @return count of updated rows
     */
    int update(K id, T t);

    /**
     * Delete entity method
     * @param id id of the entity to delete
     * @return count of deleted rows
     */
    int delete(K id);
}
