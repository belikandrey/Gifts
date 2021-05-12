package com.epam.esm.dao;

import com.epam.esm.dao.criteria.SearchCriteria;
import com.epam.esm.dao.pagination.PaginationSetting;
import com.epam.esm.entity.Certificate;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/** The interface Certificate dao. */
public interface CertificateDAO {

  /**
   * Find by id optional.
   *
   * @param id the id
   * @return the {@link Optional} of {@link Certificate}
   */
  Optional<Certificate> findById(BigInteger id);

  /**
   * Find all list.
   *
   * @param paginationSetting the pagination setting {@link PaginationSetting}
   * @return the list of {@link Certificate}
   */
  List<Certificate> findAll(PaginationSetting paginationSetting);

  /**
   * Save certificate.
   *
   * @param entity the {@link Certificate}
   * @return the {@link Certificate}
   */
  Certificate save(Certificate entity);

  /**
   * Update certificate.
   *
   * @param entity the {@link Certificate}
   * @return the {@link Certificate}
   */
  Certificate update(Certificate entity);

  /**
   * Delete by id.
   *
   * @param id the id
   */
  void deleteById(BigInteger id);

  /**
   * Find by criteria collection.
   *
   * @param criteria the {@link SearchCriteria}
   * @param paginationSetting the pagination setting {@link PaginationSetting}
   * @return the collection of {@link Certificate}
   */
  Collection<Certificate> findByCriteria(
      SearchCriteria criteria, PaginationSetting paginationSetting);

  /**
   * Count long.
   *
   * @return the long
   */
  Long count();
}
