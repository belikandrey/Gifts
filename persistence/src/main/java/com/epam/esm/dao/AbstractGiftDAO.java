package com.epam.esm.dao;

import com.epam.esm.dao.pagination.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public abstract class AbstractGiftDAO<T> {
  private Class<T> clazz;

  private String FIND_COUNT = "SELECT COUNT(c) FROM ";

  @PersistenceContext private EntityManager entityManager;

  public void setClazz(Class<T> clazz) {
    this.clazz = clazz;
  }

  public Optional<T> findById(BigInteger id) {
    return Optional.ofNullable(entityManager.find(clazz, id));
  }

  public List<T> findAll(Pageable pageable) {
    return entityManager
        .createQuery("from " + clazz.getName())
        .setFirstResult((pageable.getPage() - 1) * pageable.getSize())
        .setMaxResults(pageable.getSize())
        .getResultList();
  }

  public Long count() {
    return (Long)
        getEntityManager().createQuery(FIND_COUNT + clazz.getName() + " c").getSingleResult();
  }
//
//  public T save(T entity) {
//    entityManager.persist(entity);
//    return entity;
//  }
//
//  public T update(T entity) {
//    return entityManager.merge(entity);
//  }
//
//  public void deleteById(BigInteger id) {
//    final T entity = entityManager.find(clazz, id);
//    entityManager.remove(entity);
//  }

  protected EntityManager getEntityManager() {
    return entityManager;
  }
}
