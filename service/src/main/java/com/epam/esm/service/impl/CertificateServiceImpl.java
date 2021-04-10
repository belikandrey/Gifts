package com.epam.esm.service.impl;

import com.epam.esm.dao.AbstractDAO;
import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.criteria.SearchCriteria;
import com.epam.esm.dao.criteria.impl.CertificateSearchCriteria;
import com.epam.esm.dao.impl.CertificateDAOImpl;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.EntityService;
import com.epam.esm.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Certificate service
 *
 * @author Andrey Belik
 */
@Service
public class CertificateServiceImpl implements CertificateService {
  private final Validator<Certificate> validator;
  private final CertificateDAO certificateDAO;
  private final Converter<Certificate, CertificateDTO> converter;
  private final Converter<Tag, TagDTO> tagConverter;
  private final Validator<Tag> tagValidator;
  /**
   * Constructor
   *
   * @param validator {@link com.epam.esm.validator.Validator}
   * @param certificateDAO {@link com.epam.esm.dao.AbstractDAO}
   * @param converter {@link com.epam.esm.dto.converter.Converter} //* @param tagService {@link
   *     TagServiceImpl}
   */
  @Autowired
  public CertificateServiceImpl(
      Validator<Certificate> validator,
      CertificateDAO certificateDAO,
      Converter<Certificate, CertificateDTO> converter,
      Converter<Tag, TagDTO> tagConverter,
      Validator<Tag> tagValidator) {
    this.validator = validator;
    this.certificateDAO = certificateDAO;
    this.converter = converter;
    this.tagConverter = tagConverter;
    this.tagValidator = tagValidator;
  }

  /**
   * Find all certificates by tag id method
   *
   * @param tagId tag id
   * @return {@link java.util.Collection} of certificates
   */
  /*@Override
  public Collection<CertificateDTO> findAll(BigInteger tagId) {
    return certificateDAO.findAll(tagId).stream()
        .map(converter::convert)
        .collect(Collectors.toList());
  }*/

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
  @Override
  public Collection<CertificateDTO> findAll(
      String tagName, String name, String description, String sortName, String sortDate) {
    Map<String, String> params = new HashMap<>();
    params.put("tagName", tagName);
    params.put("name", name);
    params.put("description", description);
    params.put("sortName", sortName);
    params.put("sortDate", sortDate);
    final List<CertificateDTO> certificates =
        certificateDAO.findByCriteria(new CertificateSearchCriteria(params)).stream()
            .map(converter::convert)
            .collect(Collectors.toList());
    System.out.println(certificates);
    for (CertificateDTO certificate : certificates) {
      final Set<TagDTO> tags =
          certificateDAO.findTagsByCertificateId(certificate.getId()).stream()
              .map(tagConverter::convert)
              .collect(Collectors.toSet());
      certificate.setTags(tags);
    }
    System.out.println("\n");
    System.out.println(certificates);
    return certificates;
  }

  /**
   * Find all certificates method
   *
   * @return {@link java.util.Collection} of certificates
   */
  /*@Override
    public Collection<CertificateDTO> findAll() {
      return certificateDAO.findAll().stream().map(converter::convert).collect(Collectors.toList());
    }
  */
  /**
   * Find certificate by id method
   *
   * @param id id of certificate
   * @return certificate or null if certificate not found
   */
  @Override
  public CertificateDTO findById(BigInteger id) throws EntityNotFoundException {
    final Certificate certificate = certificateDAO.findById(id);
    final Set<TagDTO> tags =
        certificateDAO.findTagsByCertificateId(id).stream()
            .map(tagConverter::convert)
            .collect(Collectors.toSet());
    final CertificateDTO certificateDTO = converter.convert(certificate);
    certificateDTO.setTags(tags);
    return certificateDTO;
  }

  /**
   * Add certificate, also add tags for this certificate
   *
   * <p>//* @param giftCertificate certificate for add
   *
   * @return certificate or null if not added
   * @throws ValidatorException if certificate is invalid
   */
  @Override
  public CertificateDTO add(CertificateDTO certificateDTO) throws ValidatorException {
    final Certificate certificate = converter.convert(certificateDTO);
    certificate.setCreateDate(LocalDateTime.now());
    certificate.setLastUpdateDate(LocalDateTime.now());
    validator.validate(certificate);
    final Set<Tag> tags =
        certificateDTO.getTags().stream().map(tagConverter::convert).collect(Collectors.toSet());
    for (Tag tag : tags) {
      if (tag.getId() == null && (tag.getName() != null && !tag.getName().isEmpty())) {
        tagValidator.validate(tag);
      }
    }
    final CertificateDTO certificateFromDao =
        converter.convert(certificateDAO.addCertificateWithTags(certificate, tags));
    final Set<TagDTO> tagsFromDb =
        certificateDAO.findTagsByCertificateId(certificateFromDao.getId()).stream()
            .map(tagConverter::convert)
            .collect(Collectors.toSet());
    certificateFromDao.setTags(tagsFromDb);
    return certificateFromDao;
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
  public void update(BigInteger certificateId, CertificateDTO giftCertificate)
      throws ValidatorException, EntityNotFoundException {
    giftCertificate.setLastUpdateDate(LocalDateTime.now());
    final Certificate certificateForUpdate = converter.convert(giftCertificate);
    final CertificateDTO certificateDTO = findById(certificateId);
    final Certificate certificate = fillForInsert(certificateForUpdate, certificateDTO);
    validator.validate(certificate);
    certificateDAO.update(certificateId, certificate);
  }

  private Certificate fillForInsert(
      Certificate certificateForUpdate, CertificateDTO certificateFromDb) {
    if (certificateForUpdate.getName() == null || certificateForUpdate.getName().isEmpty()) {
      certificateForUpdate.setName(certificateFromDb.getName());
    }
    if (certificateForUpdate.getDescription() == null
        || certificateForUpdate.getDescription().isEmpty()) {
      certificateForUpdate.setDescription(certificateFromDb.getDescription());
    }
    if (certificateForUpdate.getPrice() == null) {
      certificateForUpdate.setPrice(certificateFromDb.getPrice());
    }
    if (certificateForUpdate.getDuration() == 0) {
      certificateForUpdate.setDuration(certificateFromDb.getDuration());
    }
    return certificateForUpdate;
  }

  /**
   * Delete certificate by id method
   *
   * @param id id of certificate for delete
   * @return true if certificate deleted, false in another way
   */
  @Override
  public void delete(BigInteger id) throws EntityNotFoundException {
    certificateDAO.delete(id);
  }
}
