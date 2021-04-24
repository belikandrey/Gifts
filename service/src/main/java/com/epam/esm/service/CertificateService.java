package com.epam.esm.service;

import com.epam.esm.dao.pagination.Pageable;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.exception.ValidatorException;

import java.math.BigInteger;
import java.util.Collection;

/**
 * Base interface for Certificate service
 *
 * @see EntityService
 * @version 1.0
 * @author Andrey Belik
 */
public interface CertificateService extends EntityService<CertificateDTO, BigInteger> {
  /**
   * Find all certificates by params method
   *
   * @param tagName name of tag
   * @param name name of certificate
   * @param description description of certificate
   * @param sortName sort by name type(asc, desc)
   * @param sortDate sort by date type(asc, desc)
   * @param pageable
   * @return {@link Collection} of {@link CertificateDTO}
   */
  Collection<CertificateDTO> findAll(
          String tagName, String name, String description, String sortName, String sortDate, Pageable pageable);

  void update(BigInteger id, CertificateDTO t, boolean isFullUpdate) throws ValidatorException;
}
