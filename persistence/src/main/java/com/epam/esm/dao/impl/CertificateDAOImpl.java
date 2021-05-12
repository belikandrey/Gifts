package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractGiftDAO;
import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.criteria.SearchCriteria;
import com.epam.esm.dao.pagination.PaginationSetting;
import com.epam.esm.entity.Certificate;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Collection;

/**
 * Class that interacts with the database
 *
 * @version 1.0
 * @author Andrey Belik
 * @see com.epam.esm.dao.AbstractGiftDAO
 * @see com.epam.esm.dao.CertificateDAO
 */
@Repository
public class CertificateDAOImpl extends AbstractGiftDAO<Certificate> implements CertificateDAO {

  /**
   * Setter for Class field
   *
   * @see Class
   */
  public CertificateDAOImpl() {
    setClazz(Certificate.class);
  }

  /**
   * Find by criteria collection.
   *
   * @param criteria the {@link SearchCriteria}
   * @param paginationSetting the pagination setting {@link PaginationSetting}
   * @return collection
   */
  @Override
  public Collection<Certificate> findByCriteria(
      SearchCriteria criteria, PaginationSetting paginationSetting) {
    return getEntityManager()
        .createNativeQuery(criteria.getQuery(), Certificate.class)
        .setFirstResult((paginationSetting.getPage() - 1) * paginationSetting.getSize())
        .setMaxResults(paginationSetting.getSize())
        .getResultList();
  }

  /**
   * Save certificate.
   *
   * @param entity the {@link Certificate}
   * @return the {@link Certificate}
   */
  @Override
  public Certificate save(Certificate entity) {
    getEntityManager().persist(entity);
    return entity;
  }

  /**
   * Update certificate.
   *
   * @param entity the {@link Certificate}
   * @return the {@link Certificate}
   */
  @Override
  public Certificate update(Certificate entity) {
    return getEntityManager().merge(entity);
  }

  /**
   * Delete by id.
   *
   * @param id the id {@link BigInteger}
   */
  @Override
  public void deleteById(BigInteger id) {
    final Certificate certificate = findById(id).orElseThrow();
    certificate.setEnabled(false);
    update(certificate);
  }
}
