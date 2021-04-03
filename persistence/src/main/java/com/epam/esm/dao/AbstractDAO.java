package com.epam.esm.dao;

import com.epam.esm.entity.Entity;

import java.util.Collection;

public interface AbstractDAO<T extends Entity> {
    Collection<T> findAll();

    Collection<T> findAll(int id);

    T find(int id);

    void add(T t);

    void update(int id, T t);

    void delete(int id);
}
