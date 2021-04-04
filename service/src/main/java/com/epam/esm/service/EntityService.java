package com.epam.esm.service;

import com.epam.esm.exception.ValidatorException;

import java.util.Collection;

public interface EntityService<T, K> {
    Collection<T> findAll();

    Collection<T> findAll(K id);

    T find(K id);

    T add(T t) throws ValidatorException;

    int update(K id, T t) throws ValidatorException;

    int delete(K id);
}
