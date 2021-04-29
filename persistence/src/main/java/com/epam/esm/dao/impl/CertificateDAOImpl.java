package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractGiftDAO;
import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.criteria.SearchCriteria;
import com.epam.esm.dao.pagination.PaginationSetting;
import com.epam.esm.entity.Certificate;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Collection;

@Repository
public class CertificateDAOImpl extends AbstractGiftDAO<Certificate> implements CertificateDAO {

  public CertificateDAOImpl() {
    setClazz(Certificate.class);
  }

  @Override
  public Collection<Certificate> findByCriteria(
      SearchCriteria criteria, PaginationSetting paginationSetting) {
    return getEntityManager()
        .createNativeQuery(criteria.getQuery(), Certificate.class)
        .setFirstResult((paginationSetting.getPage() - 1) * paginationSetting.getSize())
        .setMaxResults(paginationSetting.getSize())
        .getResultList();
  }

  @Override
  public Certificate save(Certificate entity) {
    getEntityManager().persist(entity);
    return entity;
  }

  @Override
  public Certificate update(Certificate entity) {
    return getEntityManager().merge(entity);
  }

  @Override
  public void deleteById(BigInteger id) {
    final Certificate certificate = findById(id).get();
    certificate.setEnabled(false);
    update(certificate);
  }
}
