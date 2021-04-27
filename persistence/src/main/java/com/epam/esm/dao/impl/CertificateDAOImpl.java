package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.criteria.SearchCriteria;
import com.epam.esm.dao.pagination.Pageable;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Optional;

/**
 * Class that interacts with the database
 *
 * @author Andrey Belik
 * @version 1.0
 */
@Repository
public class CertificateDAOImpl implements CertificateDAO {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Optional<Certificate> findById(BigInteger id) {
    return Optional.ofNullable(entityManager.find(Certificate.class, id));
  }

  @Override
  public Certificate add(Certificate certificate) {
    certificate.setId(null);
    certificate = entityManager.merge(certificate);
    return certificate;
  }

  @Override
  public Certificate update(BigInteger id, Certificate certificate) {
    certificate.setId(id);
    return entityManager.merge(certificate);
  }

  @Override
  public boolean delete(BigInteger id) {
    final Certificate certificate = entityManager.find(Certificate.class, id);
    entityManager.remove(certificate);
    return true;
  }

  @Override
  public Collection<Certificate> findByCriteria(SearchCriteria criteria, Pageable pageable) {
    return entityManager
        .createNativeQuery(criteria.getQuery(), Certificate.class)
        .setFirstResult((pageable.getPage() - 1) * pageable.getSize())
        .setMaxResults(pageable.getSize())
        .getResultList();
  }

  @Override
  public boolean addCertificateTag(BigInteger certificateId, BigInteger tagId) {
    final Certificate certificate = entityManager.find(Certificate.class, certificateId);
    final Tag tag = entityManager.find(Tag.class, tagId);
    certificate.getTags().add(tag);
    return true;
  }

  @Override
  public boolean deleteCertificateTag(BigInteger certificateId, BigInteger tagId) {
    final Certificate certificate = entityManager.find(Certificate.class, certificateId);
    final Tag tag = entityManager.find(Tag.class, tagId);
    certificate.getTags().remove(tag);
    return true;
  }
}
