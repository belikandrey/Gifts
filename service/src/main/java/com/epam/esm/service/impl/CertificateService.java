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
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CertificateService implements EntityService<CertificateDTO, BigInteger> {
    private final Validator<Certificate> validator;
    private final AbstractDAO<Certificate, BigInteger> certificateDAO;
    private final Converter<Certificate, CertificateDTO> converter;
    private final TagService tagService;

    @Autowired
    public CertificateService(Validator<Certificate> validator, AbstractDAO<Certificate, BigInteger> certificateDAO,
                              TagService tagService,
                              Converter<Certificate, CertificateDTO> converter) {
        this.validator = validator;
        this.certificateDAO = certificateDAO;
        this.converter = converter;
        this.tagService = tagService;
    }

    @Override
    public Collection<CertificateDTO> findAll(BigInteger id) {
        return certificateDAO.findAll(id).stream().map(converter::convert).collect(Collectors.toList());
    }

    public Collection<CertificateDTO> findAll(String tagName) {
        return ((CertificateDAO) certificateDAO).findAll(tagName).stream().map(converter::convert).collect(Collectors.toList());
    }

    public Collection<CertificateDTO> findAll(String tagName, String name, String description, String sortName, String sortDate) {
        if (tagName == null && name == null && description == null && sortName == null && sortDate == null) {
            return findAll();
        }
        return ((CertificateDAO) certificateDAO).findAll(tagName, name, description, sortName, sortDate).
                stream().map(converter::convert).peek(p -> p.setTagsDTO((List<TagDTO>) tagService.findAll(p.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<CertificateDTO> findAll() {
        return certificateDAO.findAll().stream().map(converter::convert).collect(Collectors.toList());
    }

    @Override
    public CertificateDTO find(BigInteger id) {
        final Certificate certificate = certificateDAO.find(id);
        return certificate != null ? converter.convert(certificate) : null;
    }

    @Override
    public CertificateDTO add(CertificateDTO giftCertificate) throws ValidatorException {
        giftCertificate.setCreateDate(LocalDateTime.now());
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        final Certificate certificate = converter.convert(giftCertificate);
        validator.validate(certificate);
        return certificateDAO.add(certificate) > 0 ? giftCertificate : null;
    }

    @Override
    public int update(BigInteger id, CertificateDTO giftCertificate) throws ValidatorException {
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        final Certificate certificate = converter.convert(giftCertificate);
        validator.validate(certificate);
        return certificateDAO.update(id, certificate);
    }

    @Override
    public int delete(BigInteger id) {
        return certificateDAO.delete(id);
    }
}
