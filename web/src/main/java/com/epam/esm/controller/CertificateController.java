package com.epam.esm.controller;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.service.impl.CertificateService;
import com.epam.esm.service.impl.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/certificates")
public class CertificateController {

    private CertificateService certificateService;

    @Autowired
    public CertificateController(CertificateService certificateService, TagService tagService) {
        this.certificateService = certificateService;
    }

    @GetMapping()
    public ResponseEntity<?> findAll(@RequestParam(value = "tag", required = false) BigInteger id) {
        Collection<CertificateDTO> giftCertificates;
        if (id != null) {
            giftCertificates = certificateService.findAll(id);
        } else {
            giftCertificates = certificateService.findAll();
        }
        return new ResponseEntity<>(giftCertificates, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable("id") BigInteger id) {
        final CertificateDTO certificate = certificateService.find(id);
        return certificate != null ?
                new ResponseEntity<>(certificate, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody CertificateDTO giftCertificate) {
        try {
            certificateService.add(giftCertificate);
            return new ResponseEntity<>(giftCertificate, HttpStatus.OK);
        } catch (ValidatorException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") BigInteger id) {
        if (certificateService.delete(id) > 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") BigInteger id, @RequestBody CertificateDTO newCertificate) {
        try {
            certificateService.update(id, newCertificate);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ValidatorException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
