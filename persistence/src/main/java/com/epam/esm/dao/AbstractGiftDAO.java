package com.epam.esm.dao;

import com.epam.esm.dao.pagination.PaginationSetting;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * The type Abstract gift dao. Class that abstract interact with database
 *
 * @param <T> the type parameter
 */
public abstract class AbstractGiftDAO<T> {
  /** The Clazz. */
  private Class<T> clazz;

  /** The Find count. */
  private static final String FIND_COUNT = "SELECT COUNT(c) FROM ";

  /** The Entity manager. */
  @PersistenceContext private EntityManager entityManager;

  /**
   * Sets clazz.
   *
   * @param clazz the clazz
   */
  public void setClazz(Class<T> clazz) {
    this.clazz = clazz;
  }

  /**
   * Find by id optional.
   *
   * @param id the id
   * @return the optional
   */
  public Optional<T> findById(BigInteger id) {
    return Optional.ofNullable(entityManager.find(clazz, id));
  }

  /**
   * Find all list.
   *
   * @param paginationSetting the pagination setting
   * @return the list
   */
  public List<T> findAll(PaginationSetting paginationSetting) {
    return entityManager
        .createQuery("from " + clazz.getName())
        .setFirstResult((paginationSetting.getPage() - 1) * paginationSetting.getSize())
        .setMaxResults(paginationSetting.getSize())
        .getResultList();
  }

  /**
   * Count long.
   *
   * @return the long
   */
  public Long count() {
    return (Long)
        getEntityManager().createQuery(FIND_COUNT + clazz.getName() + " c").getSingleResult();
  }

  /**
   * Gets entity manager.
   *
   * @return the entity manager
   */
  protected EntityManager getEntityManager() {
    return entityManager;
  }
}
