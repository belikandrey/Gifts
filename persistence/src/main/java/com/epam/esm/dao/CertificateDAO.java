package com.epam.esm.dao;

import com.epam.esm.dao.criteria.SearchCriteria;
import com.epam.esm.entity.Certificate;

import java.math.BigInteger;
import java.util.Collection;

/**
 * Base Certificate DAO interface
 *
 * @see com.epam.esm.dao.AbstractDAO
 * @version 1.0
 * @author Andrey Belik
 */
public interface CertificateDAO extends AbstractDAO<Certificate, BigInteger> {
  /**
   * Find by {@link SearchCriteria} method
   *
   * @param criteria {@link SearchCriteria} for searching by
   * @return {@link Collection} of certificates
   */
  Collection<Certificate> findByCriteria(SearchCriteria criteria);

  /**
   * Add certificate id and tag id method
   *
   * @param certificateId id of certificate
   * @param tagId id of tag
   * @return true if added, false in another way
   */
  boolean addCertificateTag(BigInteger certificateId, BigInteger tagId);

    boolean deleteCertificateTag(BigInteger certificateId, BigInteger tagId);
}
