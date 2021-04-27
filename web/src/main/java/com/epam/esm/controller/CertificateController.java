package com.epam.esm.controller;

import com.epam.esm.dao.pagination.Pageable;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.hateoas.HateoasResolver;
import com.epam.esm.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.math.BigInteger;
import java.util.Collection;

/**
 * Rest controller for certificates
 *
 * @author Andrey Belik
 * @version 1.0
 */
@RestController
@RequestMapping("/certificates")
public class CertificateController {

  //TODO

  private final CertificateService certificateService;

  private final HateoasResolver hateoasResolver;
  /**
   * Constructor
   *
   * @param certificateService {@link com.epam.esm.service.EntityService}
   */
  @Autowired
  public CertificateController(CertificateService certificateService, HateoasResolver hateoasResolver) {
    this.certificateService = certificateService;
    this.hateoasResolver = hateoasResolver;
  }

  /**
   * Find all certificates, by params too
   *
   * @param tagName name of tag
   * @param name name of certificate
   * @param description description of certificate
   * @param sortName type of sort by name(asc, desc)
   * @param sortDate type of sort by date(asc, desc)
   * @return response entity
   */
  @GetMapping()
  public CollectionModel<CertificateDTO> findAll(
      @RequestParam(value = "tagName", required = false) String tagName,
      @RequestParam(value = "name", required = false) String name,
      @RequestParam(value = "description", required = false) String description,
      @RequestParam(value = "sortName", required = false) String sortName,
      @RequestParam(value = "sortDate", required = false) String sortDate,
      @RequestParam(name = "page", defaultValue = "1", required = false) int page,
      @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
    Pageable pageable = new Pageable(size, page);
    Collection<CertificateDTO> giftCertificates =
        certificateService.findAll(tagName, name, description, sortName, sortDate, pageable);
    giftCertificates.forEach(hateoasResolver::addLinksForCertificate);
    return hateoasResolver.getModelForCertificates(giftCertificates);
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
