package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.criteria.impl.CertificateSearchCriteria;
import com.epam.esm.dao.pagination.PaginationSetting;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
@Transactional
public class CertificateServiceImpl implements CertificateService {
  /** The Validator. */
  private final Validator<Certificate> validator;

  /** The Certificate dao. */
  private final CertificateDAO certificateDAO;

  /** The Converter. */
  private final Converter<Certificate, CertificateDTO> converter;

  /** The Tag service. */
  private final TagService tagService;

  /** The Tag converter. */
  private final Converter<Tag, TagDTO> tagConverter;
  /**
   * Constructor
   *
   * @param validator {@link com.epam.esm.validator.Validator}
   * @param converter {@link com.epam.esm.dto.converter.Converter}
   * @param tagService {@link TagService}
   * @param certificateDAO the {@link CertificateDAO}
   * @param tagConverter the tag {@link Converter}
   */
  @Autowired
  public CertificateServiceImpl(
      Validator<Certificate> validator,
      CertificateDAO certificateDAO,
      Converter<Certificate, CertificateDTO> converter,
      TagService tagService,
      Converter<Tag, TagDTO> tagConverter) {
    this.validator = validator;
    this.certificateDAO = certificateDAO;
    this.converter = converter;
    this.tagService = tagService;
    this.tagConverter = tagConverter;
  }

  /**
   * Count long.
   *
   * @return the long
   */
  @Override
  public Long count() {
    return certificateDAO.count();
  }

  /**
   * Find all certificates by params
   *
   * <p>//@param tagName name of tag
   *
   * @param tagsName the tags name
   * @param name part of certificate name
   * @param description part of description of certificate
   * @param sortName type of sort by name(asc, desc)
   * @param sortDate type of sort by date(asc, desc)
   * @param paginationSetting the pagination setting
   * @param state the state
   * @return {@link java.util.Collection} of certificates
   */
  @Override
  @Transactional(readOnly = true)
  public Collection<CertificateDTO> findAll(
      List<String> tagsName,
      String name,
      String description,
      String sortName,
      String sortDate,
      PaginationSetting paginationSetting,
      String state) {
    Map<String, Object> params =
        fillMapWithParams(tagsName, name, description, sortName, sortDate, state);
    final List<CertificateDTO> certificates =
        certificateDAO
            .findByCriteria(new CertificateSearchCriteria(params), paginationSetting)
            .stream()
            .peek(System.out::println)
            .map(converter::convertToDto)
            .collect(Collectors.toList());
    return certificates;
  }

  /**
   * Fill map with params map.
   *
   * @param tagName the tag name
   * @param name the name
   * @param description the description
   * @param sortName the sort name
   * @param sortDate the sort date
   * @param state the state
   * @return the map
   */
  private Map<String, Object> fillMapWithParams(
      List<String> tagName,
      String name,
      String description,
      String sortName,
      String sortDate,
      String state) {
    Map<String, Object> params = new HashMap<>();
    params.put("tagsName", tagName);
    params.put("name", name);
    params.put("description", description);
    params.put("sortName", sortName);
    params.put("sortDate", sortDate);
    params.put("state", state);
    return params;
  }

  /**
   * Find certificate by id method
   *
   * @param id id of certificate
   * @return certificate certificate dto
   * @exception EntityNotFoundException if certificate with this id not found
   */
  @Override
  @Transactional(readOnly = true)
  public CertificateDTO findById(BigInteger id) {
    final Optional<Certificate> certificateOptional = certificateDAO.findById(id);
    if (certificateOptional.isEmpty()) {
      throw new EntityNotFoundException(
          "Certificate with id : " + id + " not found", Certificate.class);
    }
    return converter.convertToDto(certificateOptional.get());
  }

  /**
   * Add certificate, also add tags for this certificate
   *
   * @param newCertificateDTO certificate for add
   * @return added certificate
   * @throws ValidatorException if certificate is invalid
   */
  @Override
  @Transactional(rollbackFor = ValidatorException.class)
  public CertificateDTO add(CertificateDTO newCertificateDTO) throws ValidatorException {
    Certificate certificate = converter.convertToEntity(newCertificateDTO);
    certificate.setCreateDate(LocalDateTime.now());
    certificate.setLastUpdateDate(LocalDateTime.now());
    certificate.setEnabled(true);
    validator.validate(certificate);
    final Set<TagDTO> tagsForSetIntoCertificate =
        getTagsForSetIntoCertificate(newCertificateDTO.getTags());
    certificate.setTags(
        tagsForSetIntoCertificate.stream()
            .map(tagConverter::convertToEntity)
            .collect(Collectors.toSet()));
    certificate.setId(null);
    certificate = certificateDAO.save(certificate);
    return converter.convertToDto(certificate);
  }

  /**
   * Gets tags for set into certificate.
   *
   * @param tags the tags
   * @return the tags for set into certificate
   * @throws ValidatorException the validator exception
   */
  private Set<TagDTO> getTagsForSetIntoCertificate(Set<TagDTO> tags) throws ValidatorException {
    Set<TagDTO> tagsFromDb = new HashSet<>();
    for (TagDTO tag : tags) {
      TagDTO tagFromDB;
      tagFromDB = findOrAddTag(tag);
      tagsFromDb.add(tagFromDB);
    }
    return tagsFromDb;
  }

  /**
   * Find or add tag tag dto.
   *
   * @param tag the tag
   * @return the tag dto
   * @throws ValidatorException the validator exception
   */
  private TagDTO findOrAddTag(TagDTO tag) throws ValidatorException {
    TagDTO tagFromDB;
    tagFromDB = tag.getId() != null ? tagService.findById(tag.getId()) : null;
    tagFromDB =
        tagFromDB == null && tag.getName() != null
            ? tagService.findByName(tag.getName())
            : tagFromDB;
    if (tagFromDB != null) {
      return tagFromDB;
    }
    if ((tag.getName() == null || tag.getName().isEmpty())) {
      throw new EntityNotFoundException("Tag with id " + tag.getId() + " not found", Tag.class);
    } else {
      tagFromDB = tagService.add(tag);
    }

    return tagFromDB;
  }
  /**
   * Update certificate method
   *
   * @param certificateId id of certificate for update
   * @param giftCertificate certificate for update
   * @param isFullUpdate the is full update
   * @throws ValidatorException if certificate is invalid
   * @exception EntityNotFoundException if certificate with this id not found
   */
  @Override
  @Transactional(rollbackFor = {ValidatorException.class, EntityNotFoundException.class})
  public void update(BigInteger certificateId, CertificateDTO giftCertificate, boolean isFullUpdate)
      throws ValidatorException {
    final Certificate certificateForUpdate = converter.convertToEntity(giftCertificate);
    final Optional<Certificate> certificateOptional = certificateDAO.findById(certificateId);
    if (certificateOptional.isEmpty()) {
      throw new EntityNotFoundException(
          "Certificate with id : " + certificateId + " not found", Certificate.class);
    }
    Certificate certificate = certificateOptional.get();
    if (!isFullUpdate) {
      fillForInsert(certificateForUpdate, certificate);
    }
    final Set<TagDTO> tagsForSetIntoCertificate =
        getTagsForSetIntoCertificate(giftCertificate.getTags());
    //    certificateForUpdate.setTags(
    //        giftCertificate.getTags().stream()
    //            .map(tagConverter::convertToEntity)
    //            .collect(Collectors.toSet()));
    validator.validate(certificateForUpdate);
    certificateForUpdate.setTags(
        tagsForSetIntoCertificate.stream()
            .map(tagConverter::convertToEntity)
            .collect(Collectors.toSet()));
    certificateForUpdate.setLastUpdateDate(LocalDateTime.now());
    certificateForUpdate.setId(certificateId);
    if (certificateDAO.update(certificateForUpdate) == null) {
      throw new EntityNotFoundException(
          "Certificate with id : " + certificateId + " not found", Certificate.class);
    }
  }

  /**
   * Fill for insert certificate.
   *
   * @param certificateForUpdate the certificate for update
   * @param certificateFromDb the certificate from db
   * @return the certificate
   */
  private Certificate fillForInsert(
      Certificate certificateForUpdate, Certificate certificateFromDb) {
    certificateForUpdate.setName(
        certificateForUpdate.getName() == null || certificateForUpdate.getName().isEmpty()
            ? certificateFromDb.getName()
            : certificateForUpdate.getName());

    certificateForUpdate.setDescription(
        certificateForUpdate.getDescription() == null
                || certificateForUpdate.getDescription().isEmpty()
            ? certificateFromDb.getDescription()
            : certificateForUpdate.getDescription());

    certificateForUpdate.setPrice(
        certificateForUpdate.getPrice() == null
            ? certificateFromDb.getPrice()
            : certificateForUpdate.getPrice());

    certificateForUpdate.setDuration(
        certificateForUpdate.getDuration() == null
            ? certificateFromDb.getDuration()
            : certificateForUpdate.getDuration());

    certificateForUpdate.setCreateDate(certificateFromDb.getCreateDate());
    certificateForUpdate.setEnabled(certificateFromDb.getEnabled());
    return certificateForUpdate;
  }

  /**
   * Delete certificate by id method
   *
   * @param id id of certificate for delete
   * @exception EntityNotFoundException if certificate with this id not found
   */
  @Override
  public void delete(BigInteger id) {
    if (certificateDAO.findById(id).isEmpty()) {
      throw new EntityNotFoundException(
          "Certificate with id : " + id + " not found", Certificate.class);
    }
    certificateDAO.deleteById(id);
  }
}
