package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.criteria.impl.CertificateSearchCriteria;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.Certificate;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
  public CertificateServiceImpl(
      Validator<Certificate> validator,
      CertificateDAO certificateDAO,
      Converter<Certificate, CertificateDTO> converter,
      TagService tagService) {
    this.validator = validator;
    this.certificateDAO = certificateDAO;
    this.converter = converter;
    this.tagService = tagService;
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
    for (CertificateDTO certificate : certificates) {
      final Set<TagDTO> tags = tagService.findTagsByCertificateId(certificate.getId());
      certificate.setTags(tags);
    }
    return certificates;
  }

  /**
   * Find certificate by id method
   *
   * @param id id of certificate
   * @return certificate
   * @throws EntityNotFoundException if cerificate with this id not found
   */
  @Override
  public CertificateDTO findById(BigInteger id) {
    final Optional<Certificate> certificateById = certificateDAO.findById(id);
    if (certificateById.isEmpty()) {
      throw new EntityNotFoundException("Certificate with id : " + id + " not found");
    }
    final Certificate certificate = certificateById.get();
    final Set<TagDTO> tags = tagService.findTagsByCertificateId(id);
    final CertificateDTO certificateDTO = converter.convert(certificate);
    certificateDTO.setTags(tags);
    return certificateDTO;
  }

  /**
   * Add certificate, also add tags for this certificate
   *
   * @param newCertificateDTO certificate for add
   * @return added certificate
   * @throws ValidatorException if certificate is invalid
   */
  @Override
  @Transactional(rollbackFor = ValidatorException.class, propagation = Propagation.REQUIRES_NEW)
  public CertificateDTO add(CertificateDTO newCertificateDTO) throws ValidatorException {
    Certificate certificate = converter.convert(newCertificateDTO);
    certificate.setCreateDate(LocalDateTime.now());
    certificate.setLastUpdateDate(LocalDateTime.now());
    validator.validate(certificate);
    final CertificateDTO addedCertificateDTO = converter.convert(certificateDAO.add(certificate));
    final Set<TagDTO> tags = newCertificateDTO.getTags();
    addedCertificateDTO.setTags(getTagsForNewCertificate(addedCertificateDTO.getId(), tags));
    return addedCertificateDTO;
  }

  private Set<TagDTO> getTagsForNewCertificate(BigInteger certificateId, Set<TagDTO> tags)
      throws ValidatorException {
    Set<TagDTO> tagsFromDb = new HashSet<>();
    for (TagDTO tag : tags) {
      TagDTO tagFromDB;
      if (tagService.isAlreadyExists(tag)) {
        tagFromDB =
            tag.getId() != null
                ? tagService.findById(tag.getId())
                : tagService.findByName(tag.getName());
      } else {
        tagFromDB = tagService.add(tag);
      }
      certificateDAO.addCertificateTag(certificateId, tagFromDB.getId());
      tagsFromDb.add(tagFromDB);
    }
    return tagsFromDb;
  }
  // TODO read about transactions

  /**
   * Update certificate method
   *
   * @param certificateId id of certificate for update
   * @param giftCertificate certificate for update
   * @throws ValidatorException if certificate is invalid
   * @throws EntityNotFoundException if certificate with this id not found
   */
  @Override
  public void update(BigInteger certificateId, CertificateDTO giftCertificate)
      throws ValidatorException {
    final Certificate certificateForUpdate = converter.convert(giftCertificate);
    final CertificateDTO certificateDTO = findById(certificateId);
    final Certificate certificate = fillForInsert(certificateForUpdate, certificateDTO);
    validator.validate(certificate);
    certificate.setLastUpdateDate(LocalDateTime.now());
    if (!certificateDAO.update(certificateId, certificate)) {
      throw new EntityNotFoundException("Certificate with id : " + certificateId + " not found");
    }
  }

  private Certificate fillForInsert(
      Certificate certificateForUpdate, CertificateDTO certificateFromDb) {
    if (certificateForUpdate.getName() == null || certificateForUpdate.getName().isEmpty()) {
      certificateForUpdate.setName(certificateFromDb.getName());
    }
    // TODO implement functional interface
    if (certificateForUpdate.getDescription() == null
        || certificateForUpdate.getDescription().isEmpty()) {
      certificateForUpdate.setDescription(certificateFromDb.getDescription());
    }
    if (certificateForUpdate.getPrice() == null) {
      certificateForUpdate.setPrice(certificateFromDb.getPrice());
    }
    if (certificateForUpdate.getDuration() == null) {
      certificateForUpdate.setDuration(certificateFromDb.getDuration());
    }
    return certificateForUpdate;
  }

  /**
   * Delete certificate by id method
   *
   * @param id id of certificate for delete
   * @throws EntityNotFoundException if certificate with this id not found
   */
  @Override
  public void delete(BigInteger id) {
    if (!certificateDAO.delete(id)) {
      throw new EntityNotFoundException("Certificate with id : " + id + " not found");
    }
  }
}
