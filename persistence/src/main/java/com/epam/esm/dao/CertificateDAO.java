package com.epam.esm.dao;

import com.epam.esm.dao.criteria.SearchCriteria;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Set;

public interface CertificateDAO extends AbstractDAO<Certificate, BigInteger> {
    Collection<Certificate> findByCriteria(SearchCriteria criteria);

    Set<Tag> findTagsByCertificateId(BigInteger certificateId);
    Certificate addCertificateWithTags(Certificate certificate, Collection<Tag> tags);
}
