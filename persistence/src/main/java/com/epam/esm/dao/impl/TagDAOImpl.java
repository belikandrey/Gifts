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
  private static final String FIND_MOST_POPULAR_TAG =
      "select tag.id, tag.name "
          + "from tag "
          + "join certificate_tag ct on tag.id = ct.tag_id "
          + "join certificate c on c.id = ct.certificate_id "
          + "join order_certificate oc on c.id = oc.certificate_id "
          + "join user_order uo on oc.order_id = uo.id "
          + "where user_id = (select user.id "
          + "from user "
          + "join user_order uo on user.id = uo.user_id "
          + "group by (user_id) "
          + "order by MAX(price) desc "
          + "limit 1) "
          + "group by (tag.id)"
          + "order by count(tag_id) desc limit 1";

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

  @Override
  public Optional<Tag> findMostPopularTag() {
    return getEntityManager().createNativeQuery(FIND_MOST_POPULAR_TAG, Tag.class).getResultStream().findAny();
  }
}
