package com.epam.esm.controller;

import com.epam.esm.dao.pagination.PaginationSetting;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.hateoas.HateoasResolver;
import com.epam.esm.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

/**
 * Rest controller for certificates
 *
 * @author Andrey Belik
 * @version 1.0
 */
@RestController
@RequestMapping("/certificates")
public class CertificateController {

  /** The Certificate service. */
  private final CertificateService certificateService;

  /** The Hateoas resolver. */
  private final HateoasResolver hateoasResolver;
  /**
   * Constructor
   *
   * @param certificateService {@link com.epam.esm.service.EntityService}
   * @param hateoasResolver the hateoas resolver
   */
  @Autowired
  public CertificateController(
      CertificateService certificateService, HateoasResolver hateoasResolver) {
    this.certificateService = certificateService;
    this.hateoasResolver = hateoasResolver;
  }

  /**
   * Find all certificates, by params too
   *
   * @param tagsName the tags name
   * @param name name of certificate
   * @param description description of certificate
   * @param sortName type of sort by name(asc, desc)
   * @param sortDate type of sort by date(asc, desc)
   * @param page the page
   * @param size the size
   * @param state the state
   * @return response entity
   */
  @GetMapping()
  public CollectionModel<CertificateDTO> findAll(
      @RequestParam(value = "tagName", required = false) List<String> tagsName,
      @RequestParam(value = "name", required = false) String name,
      @RequestParam(value = "description", required = false) String description,
      @RequestParam(value = "sortName", required = false) String sortName,
      @RequestParam(value = "sortDate", required = false) String sortDate,
      @RequestParam(name = "page", defaultValue = "1", required = false) int page,
      @RequestParam(name = "size", defaultValue = "10", required = false) int size,
      @RequestParam(name = "state", defaultValue = "enabled", required = false) String state) {
    PaginationSetting paginationSetting = PaginationSetting.getInstance(size, page);
    Collection<CertificateDTO> giftCertificates =
        certificateService.findAll(
            tagsName, name, description, sortName, sortDate, paginationSetting, state);
    giftCertificates.forEach(hateoasResolver::addLinksForCertificate);
    final Long count = certificateService.count();
    return hateoasResolver.getModelForCertificates(giftCertificates, paginationSetting, count);
  }

  /**
   * Find certificate by id
   *
   * @param id id of certificate
   * @return response entity
   */
  @GetMapping("/{id}")
  public ResponseEntity<?> find(@PathVariable("id") BigInteger id) {
    final CertificateDTO certificate = certificateService.findById(id);
    hateoasResolver.addLinksForCertificate(certificate);
    return new ResponseEntity<>(certificate, HttpStatus.OK);
  }

  /**
   * Create certificate method
   *
   * @param certificate certificate for create
   * @return response entity
   * @throws ValidatorException if entity is invalid
   */
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> create(@RequestBody CertificateDTO certificate)
      throws ValidatorException {
    certificate = certificateService.add(certificate);
    hateoasResolver.addLinksForCertificate(certificate);
    return new ResponseEntity<>(certificate, HttpStatus.OK);
  }

  /**
   * Delete certificate by id method
   *
   * @param id id of certificate
   * @return response entity
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable("id") BigInteger id) {
    certificateService.delete(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  /**
   * Update certificate method
   *
   * @param id id of certificate to be updated
   * @param newCertificate certificate for update
   * @return response entity
   * @throws ValidatorException if entity is invalid
   */
  @PatchMapping("/{id}")
  public ResponseEntity<?> update(
      @PathVariable("id") BigInteger id, @RequestBody CertificateDTO newCertificate)
      throws ValidatorException {
    certificateService.update(id, newCertificate, false);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * Update all fields response entity.
   *
   * @param id the id
   * @param certificateDTO the certificate dto
   * @return the response entity
   * @throws ValidatorException the validator exception
   */
  @PutMapping("/{id}")
  public ResponseEntity<?> updateAllFields(
      @PathVariable("id") BigInteger id, @RequestBody CertificateDTO certificateDTO)
      throws ValidatorException {
    certificateService.update(id, certificateDTO, true);
    CertificateDTO certificate = certificateService.findById(id);
    hateoasResolver.addLinksForCertificate(certificateDTO);
    return new ResponseEntity<>(certificate, HttpStatus.OK);
  }
}
