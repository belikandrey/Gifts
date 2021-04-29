package com.epam.esm.dao;

import com.epam.esm.dao.pagination.PaginationSetting;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

  public List<T> findAll(PaginationSetting paginationSetting) {
    return entityManager
        .createQuery("from " + clazz.getName())
        .setFirstResult((paginationSetting.getPage() - 1) * paginationSetting.getSize())
        .setMaxResults(paginationSetting.getSize())
        .getResultList();
  }

  public Long count() {
    return (Long)
        getEntityManager().createQuery(FIND_COUNT + clazz.getName() + " c").getSingleResult();
  }

  protected EntityManager getEntityManager() {
    return entityManager;
  }
}
