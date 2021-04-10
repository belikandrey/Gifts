package com.epam.esm.service;

import com.epam.esm.dao.criteria.SearchCriteria;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;

import java.math.BigInteger;
import java.util.Collection;

public interface CertificateService extends EntityService<CertificateDTO, BigInteger>{
    Collection<CertificateDTO> findAll(String tagName, String name, String description, String sortName, String sortDate);
}
