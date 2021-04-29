package com.epam.esm.service;

import com.epam.esm.dao.pagination.PaginationSetting;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.exception.ValidatorException;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

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
   * @param paginationSetting
   * @return {@link Collection} of {@link CertificateDTO}
   */
  Collection<CertificateDTO> findAll(
      List<String> tagName,
      String name,
      String description,
      String sortName,
      String sortDate,
      PaginationSetting paginationSetting,
      String state);

  void update(BigInteger id, CertificateDTO t, boolean isFullUpdate) throws ValidatorException;

  Long count();

  CertificateDTO add(CertificateDTO certificateDTO);

  void delete(BigInteger id);
}
