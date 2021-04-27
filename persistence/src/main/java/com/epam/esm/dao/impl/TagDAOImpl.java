package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractGiftDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public class TagDAOImpl extends AbstractGiftDAO<Tag> implements TagDAO {

  private static final String FIND_BY_CERTIFICATE_ID =
      "select t from Tag t join t.certificate c where c.id = :cert_id";
  private static final String FIND_BY_NAME = "from Tag where name =:tag_name";

  public TagDAOImpl() {
    setClazz(Tag.class);
  }

  @Override
  public List<Tag> findTagsByCertificateId(BigInteger certificateId) {
    return getEntityManager()
        .createQuery(FIND_BY_CERTIFICATE_ID)
        .setParameter("cert_id", certificateId)
        .getResultList();
  }

  @Override
  public Optional<Tag> findTagByName(String name) {
    return getEntityManager()
        .createQuery(FIND_BY_NAME)
        .setParameter("tag_name", name)
        .getResultStream()
        .findAny();
  }

  @Override
  public boolean isAlreadyExist(Tag tag) {
    return false;
  }
}
