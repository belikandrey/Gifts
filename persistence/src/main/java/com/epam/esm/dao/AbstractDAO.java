package com.epam.esm.dao;

import com.epam.esm.entity.Entity;

import java.util.Collection;

public interface AbstractDAO<T extends Entity, K> {
    Collection<T> findAll();

    Collection<T> findAll(K id);

    T find(K id);

    int add(T t);

    int update(K id, T t);

    int delete(K id);
}
