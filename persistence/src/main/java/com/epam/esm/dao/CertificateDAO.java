package com.epam.esm.dao;

import com.epam.esm.dao.criteria.SearchCriteria;
import com.epam.esm.dao.pagination.Pageable;
import com.epam.esm.entity.Certificate;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CertificateDAO {

  Optional<Certificate> findById(BigInteger id);

  List<Certificate> findAll(Pageable pageable);

  Certificate save(Certificate entity);

  Certificate update(Certificate entity);

  void deleteById(BigInteger id);

  Collection<Certificate> findByCriteria(SearchCriteria criteria, Pageable pageable);
}
