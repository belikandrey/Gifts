package com.epam.esm.controller;

import com.epam.esm.entity.Certificate;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.service.impl.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/certificates")
public class CertificateController {

    private CertificateService certificateService;

    @Autowired
    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping()
    public ResponseEntity<?> findAll(@RequestParam(value = "tag", required = false) Integer id) {
        Collection<Certificate> giftCertificates;
        if (id != null) {
            giftCertificates = certificateService.findAll(id);
        } else {
            giftCertificates = certificateService.findAll();
        }
        return giftCertificates != null && !giftCertificates.isEmpty() ?
                new ResponseEntity<>(giftCertificates, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable("id") int id) {
        final Certificate certificate = certificateService.find(id);
        return certificate != null ?
                new ResponseEntity<>(certificate, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody Certificate giftCertificate) {
        try {
            certificateService.add(giftCertificate);
            return new ResponseEntity<>(giftCertificate, HttpStatus.CREATED);
        } catch (ValidatorException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        certificateService.delete(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody Certificate newCertificate) {
        try {
            certificateService.update(id, newCertificate);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (ValidatorException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
