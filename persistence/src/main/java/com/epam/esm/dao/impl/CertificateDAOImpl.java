package com.epam.esm.dao.impl;

import com.epam.esm.dao.criteria.SearchCriteria;
import com.epam.esm.dao.pagination.Pageable;
import com.epam.esm.dao.AbstractGiftDAO;
import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.entity.Certificate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Repository
public class CertificateDAOImpl extends AbstractGiftDAO<Certificate> implements CertificateDAO {

  public CertificateDAOImpl() {
    setClazz(Certificate.class);
  }

  @Override
  public Collection<Certificate> findByCriteria(SearchCriteria criteria, Pageable pageable) {
    return getEntityManager()
        .createNativeQuery(criteria.getQuery(), Certificate.class)
        .setFirstResult((pageable.getPage() - 1) * pageable.getSize())
        .setMaxResults(pageable.getSize())
        .getResultList();
  }
}
