package com.epam.esm.service.impl;

import com.epam.esm.dao.AbstractDAO;
import com.epam.esm.dao.impl.CertificateDAO;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.Certificate;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.service.EntityService;
import com.epam.esm.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Certificate service
 *
 * @author Andrey Belik
 */
@Service
public class CertificateService implements EntityService<CertificateDTO, BigInteger> {
  private final Validator<Certificate> validator;
  private final AbstractDAO<Certificate, BigInteger> certificateDAO;
  private final Converter<Certificate, CertificateDTO> converter;
  private final TagService tagService;

  /**
   * Constructor
   *
   * @param validator {@link com.epam.esm.validator.Validator}
   * @param certificateDAO {@link com.epam.esm.dao.AbstractDAO}
   * @param converter {@link com.epam.esm.dto.converter.Converter}
   * @param tagService {@link TagService}
   */
  @Autowired
  public CertificateService(
      Validator<Certificate> validator,
      AbstractDAO<Certificate, BigInteger> certificateDAO,
      TagService tagService,
      Converter<Certificate, CertificateDTO> converter) {
    this.validator = validator;
    this.certificateDAO = certificateDAO;
    this.converter = converter;
    this.tagService = tagService;
  }

  /**
   * Find all certificates by tag id method
   *
   * @param tagId tag id
   * @return {@link java.util.Collection} of certificates
   */
  @Override
  public Collection<CertificateDTO> findAll(BigInteger tagId) {
    return certificateDAO.findAll(tagId).stream()
        .map(converter::convert)
        .collect(Collectors.toList());
  }

  /**
   * Find all certificates by params
   *
   * @param tagName name of tag
   * @param name part of certificate name
   * @param description part of description of certificate
   * @param sortName type of sort by name(asc, desc)
   * @param sortDate type of sort by date(asc, desc)
   * @return {@link java.util.Collection} of certificates
   */
  public Collection<CertificateDTO> findAll(
      String tagName, String name, String description, String sortName, String sortDate) {
    if (tagName == null
        && name == null
        && description == null
        && sortName == null
        && sortDate == null) {
      return findAll();
    }
    return ((CertificateDAO) certificateDAO)
        .findAll(tagName, name, description, sortName, sortDate).stream()
            .map(converter::convert)
            .peek(p -> p.setTagsDTO((Set<TagDTO>) tagService.findAll(p.getId())))
            .collect(Collectors.toList());
  }

  /**
   * Find all certificates method
   *
   * @return {@link java.util.Collection} of certificates
   */
  @Override
  public Collection<CertificateDTO> findAll() {
    return certificateDAO.findAll().stream().map(converter::convert).collect(Collectors.toList());
  }

  /**
   * Find certificate by id method
   *
   * @param id id of certificate
   * @return certificate or null if certificate not found
   */
  @Override
  public CertificateDTO find(BigInteger id) {
    final Certificate certificate = certificateDAO.find(id);
    return certificate != null ? converter.convert(certificate) : null;
  }

  /**
   * Add certificate, also add tags for this certificate
   *
   * @param giftCertificate certificate for add
   * @return certificate or null if not added
   * @throws ValidatorException if certificate is invalid
   */
  @Override
  public CertificateDTO add(CertificateDTO giftCertificate) throws ValidatorException {
    giftCertificate.setCreateDate(LocalDateTime.now());
    giftCertificate.setLastUpdateDate(LocalDateTime.now());
    final Certificate certificate = converter.convert(giftCertificate);
    final Set<TagDTO> tagsDTO = giftCertificate.getTagsDTO();
    validator.validate(certificate);
    if (tagsDTO != null) {
      tagService.add(tagsDTO, certificate.getId());
    }
    return certificateDAO.add(certificate) > 0 ? giftCertificate : null;
  }

  /**
   * Update certificate method
   *
   * @param certificateId id of certificate for update
   * @param giftCertificate certificate for update
   * @return count of updated rows
   * @throws ValidatorException if certificate is invalid
   */
  @Override
  public int update(BigInteger certificateId, CertificateDTO giftCertificate)
      throws ValidatorException {
    giftCertificate.setLastUpdateDate(LocalDateTime.now());
    final Certificate certificate = converter.convert(giftCertificate);
    validator.validate(certificate);
    final Set<TagDTO> tagsDTO = giftCertificate.getTagsDTO();
    if (tagsDTO != null) {
      tagService.add(tagsDTO, certificateId);
    }
    return certificateDAO.update(certificateId, certificate);
  }

  /**
   * Delete certificate by id method
   *
   * @param id id of certificate for delete
   * @return true if certificate deleted, false in another way
   */
  @Override
  public boolean delete(BigInteger id) {
    return certificateDAO.delete(id) > 0;
  }
}
