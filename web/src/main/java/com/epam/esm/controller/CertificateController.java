package com.epam.esm.controller;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.service.EntityService;
import com.epam.esm.service.impl.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

  private final EntityService<CertificateDTO, BigInteger> certificateService;

  /**
   * Constructor
   *
   * @param certificateService {@link com.epam.esm.service.EntityService}
   */
  @Autowired
  public CertificateController(EntityService<CertificateDTO, BigInteger> certificateService) {
    this.certificateService = certificateService;
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
  public ResponseEntity<?> findAll(
      @RequestParam(value = "tagName", required = false) String tagName,
      @RequestParam(value = "name", required = false) String name,
      @RequestParam(value = "description", required = false) String description,
      @RequestParam(value = "sortName", required = false) String sortName,
      @RequestParam(value = "sortDate", required = false) String sortDate) {
    Collection<CertificateDTO> giftCertificates =
        ((CertificateService) certificateService)
            .findAll(tagName, name, description, sortName, sortDate);
    return new ResponseEntity<>(giftCertificates, HttpStatus.OK);
  }

  /**
   * Find certificate by id
   *
   * @param id id of certificate
   * @return response entity
   */
  @GetMapping("/{id}")
  public ResponseEntity<?> find(@PathVariable("id") BigInteger id) {
    final CertificateDTO certificate = certificateService.find(id);
    return certificate != null
        ? new ResponseEntity<>(certificate, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  /**
   * Create certificate method
   *
   * @param certificate certificate for create
   * @return response entity
   */
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> create(@RequestBody CertificateDTO certificate) {
    try {
      certificateService.add(certificate);
      return new ResponseEntity<>(certificate, HttpStatus.OK);
    } catch (ValidatorException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }

  /**
   * Delete certificate by id method
   *
   * @param id id of certificate
   * @return response entity
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable("id") BigInteger id) {
    return certificateService.delete(id)
        ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  /**
   * Update certificate method
   *
   * @param id id of certificate to be updated
   * @param newCertificate certificate for update
   * @return response entity
   */
  @PutMapping("/{id}")
  public ResponseEntity<?> update(
      @PathVariable("id") BigInteger id, @RequestBody CertificateDTO newCertificate) {
    try {
      certificateService.update(id, newCertificate);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (ValidatorException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }
}
