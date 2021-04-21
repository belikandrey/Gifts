package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.criteria.impl.CertificateSearchCriteria;
import com.epam.esm.dao1.CertificateRepository;
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
public class CertificateServiceImpl implements CertificateService {
  private final Validator<Certificate> validator;
  private final CertificateDAO certificateDAO;
  private final Converter<Certificate, CertificateDTO> converter;
  private final TagService tagService;
  private final CertificateRepository certificateRepository;
  private final Converter<Tag, TagDTO> tagConverter;
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
      TagService tagService,
      CertificateRepository certificateRepository,
      Converter<Tag, TagDTO> tagConverter) {
    this.validator = validator;
    this.certificateDAO = certificateDAO;
    this.converter = converter;
    this.tagService = tagService;
    this.certificateRepository = certificateRepository;
    this.tagConverter = tagConverter;
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
    Map<String, String> params = fillMapWithParams(tagName, name, description, sortName, sortDate);
    final List<CertificateDTO> certificates =
        certificateDAO.findByCriteria(new CertificateSearchCriteria(params)).stream()
            .map(converter::convertToDto)
            .collect(Collectors.toList());
    for (CertificateDTO certificate : certificates) {
      final Set<TagDTO> tags = tagService.findTagsByCertificateId(certificate.getId());
      certificate.setTags(tags);
    }
    return certificates;
  }

  private Map<String, String> fillMapWithParams(
      String tagName, String name, String description, String sortName, String sortDate) {
    Map<String, String> params = new HashMap<>();
    params.put("tagName", tagName);
    params.put("name", name);
    params.put("description", description);
    params.put("sortName", sortName);
    params.put("sortDate", sortDate);
    return params;
  }

  /**
   * Find certificate by id method
   *
   * @param id id of certificate
   * @return certificate
   * @exception EntityNotFoundException if certificate with this id not found
   */
  @Override
  public CertificateDTO findById(BigInteger id) {
    final Optional<Certificate> certificateOptional = certificateRepository.findById(id);
    if (certificateOptional.isEmpty()) {
      throw new EntityNotFoundException("Certificate with id : " + id + " not found");
    }
    //    final Certificate certificate = certificateOptional.get();
    //    final Set<TagDTO> tags =
    // certificate.getTags().stream().map(tagConverter::convertToDto).collect(Collectors.toSet());
    //    final CertificateDTO certificateDTO = converter.convertToDto(certificate);
    //    certificateDTO.setTags(tags);
    //    return certificateDTO;
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
    certificate.setTags(new HashSet<>());
    certificate.setCreateDate(LocalDateTime.now());
    certificate.setLastUpdateDate(LocalDateTime.now());
    validator.validate(certificate);
    certificate = certificateRepository.save(certificate);
    final CertificateDTO addedCertificateDTO = converter.convertToDto(certificate);

    final Set<TagDTO> tags = newCertificateDTO.getTags();
    certificate.setTags(getTagsForCertificate(addedCertificateDTO.getId(), tags)
    .stream().map(tagConverter::convertToEntity).collect(Collectors.toSet()));
    certificateRepository.save(certificate);
    return converter.convertToDto(certificate);
  }

  private Set<TagDTO> getTagsForCertificate(BigInteger certificateId, Set<TagDTO> tags)
      throws ValidatorException {
    Set<TagDTO> tagsFromDb = new HashSet<>();
    for (TagDTO tag : tags) {
      TagDTO tagFromDB;
      tagFromDB = findOrAddTag(tag);
      // certificateDAO.addCertificateTag(certificateId, tagFromDB.getId());
      tagsFromDb.add(tagFromDB);
    }
    return tagsFromDb;
  }

  // TODO new logic
  private TagDTO findOrAddTag(TagDTO tag) throws ValidatorException {
    TagDTO tagFromDB;
    if (tagService.isAlreadyExists(tag)) {
      tagFromDB =
          tag.getId() != null
              ? tagService.findById(tag.getId())
              : tagService.findByName(tag.getName());
    } else {
      if (tag.getName() == null || tag.getName().isEmpty()) {
        throw new ValidatorException("Tag with id " + tag.getId() + " not found");
      }
      tagFromDB = tagService.add(tag);
    }
    return tagFromDB;
  }
  /**
   * Update certificate method
   *
   * @param certificateId id of certificate for update
   * @param giftCertificate certificate for update
   * @throws ValidatorException if certificate is invalid
   * @exception EntityNotFoundException if certificate with this id not found
   */
  @Override
  @Transactional(rollbackFor = {ValidatorException.class, EntityNotFoundException.class})
  public void update(BigInteger certificateId, CertificateDTO giftCertificate)
      throws ValidatorException {
    final Certificate certificateForUpdate = converter.convertToEntity(giftCertificate);
    final CertificateDTO certificateDTO = findById(certificateId);
    final Certificate certificate = fillForInsert(certificateForUpdate, certificateDTO);
    validator.validate(certificate);
    certificate.setId(certificateId);
    certificate.setLastUpdateDate(LocalDateTime.now());
    // certificate.setTags(new HashSet<>());
    /*if (!certificateDAO.update(certificateId, certificate)) {
      throw new EntityNotFoundException("Certificate with id : " + certificateId + " not found");
    }*/
     Set<TagDTO> tagsForAdd = new HashSet<>();
    // deleteTagsForCertificate(certificateId, certificateDTO.getTags());
    for (TagDTO tagDTO : giftCertificate.getTags()) {
      if (!isContainsTag(tagDTO, certificateDTO.getTags())) {
        tagsForAdd.add(tagDTO);
      }
    }
    certificate.setTags(tagsForAdd.stream().map(tagConverter::convertToEntity).collect(Collectors.toSet()));
    certificateRepository.save(certificate);
    //getTagsForCertificate(certificateId, tagsForAdd);
  }
  /*
  private void deleteTagsForCertificate(BigInteger certificateId, Set<TagDTO> tags) {
    tags.forEach((p) -> certificateDAO.deleteCertificateTag(certificateId, p.getId()));
  }
*/
  private boolean isContainsTag(TagDTO tag, Set<TagDTO> tags) {
    Optional<TagDTO> tagDTOOptional = Optional.empty();
    if (tag.getId() != null) {
      tagDTOOptional = tags.stream().filter(p -> p.getId().equals(tag.getId())).findAny();
    }
    if (tagDTOOptional.isEmpty() && tag.getName() != null) {
      tagDTOOptional = tags.stream().filter((p) -> p.getName().equals(tag.getName())).findAny();
    }
    return tagDTOOptional.isPresent();
  }

  private Certificate fillForInsert(
      Certificate certificateForUpdate, CertificateDTO certificateFromDb) {
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
    certificateRepository.deleteById(id);
  }
}
