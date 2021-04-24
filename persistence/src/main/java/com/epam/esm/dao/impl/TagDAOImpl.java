package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Class that interacts with the database
 *
 * @author Andrey Belik
 * @version 1.0
 */
@Repository
public class TagDAOImpl implements TagDAO {

  private EntityManager entityManager;

  @Autowired
  public TagDAOImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public Collection<Tag> findAll() {
    final Query query = entityManager.createQuery("from Tag");
    return query.getResultList();
  }

  @Override
  public Optional<Tag> findById(BigInteger id) {
    return Optional.ofNullable(entityManager.find(Tag.class, id));
  }

  @Override
  public Tag add(Tag tag) {
    tag.setId(null);
    tag = entityManager.merge(tag);
    return tag;
  }

  @Override
  public Tag update(BigInteger id, Tag tag) {
    tag.setId(id);
    return entityManager.merge(tag);
  }

  @Override
  public boolean delete(BigInteger id) {
    final Tag tag = entityManager.find(Tag.class, id);
    entityManager.remove(tag);
    return true;
  }

  @Override
  public Set<Tag> findTagsByCertificateId(BigInteger certificateId) {
    return new HashSet<>(
        entityManager
            .createQuery(
                "select t from Tag t join t.certificate c where c.id = :cert_id", Tag.class)
            .setParameter("cert_id", certificateId)
            .getResultList());
  }

  @Override
  public boolean isAlreadyExist(Tag tag) {
    Optional<Tag> tagFromDb =
        tag.getId() != null
            ? findById(tag.getId())
            : tag.getName() != null ? findTagByName(tag.getName()) : Optional.empty();
    return tagFromDb.isPresent();
  }

  @Override
  public Optional<Tag> findTagByName(String name) {
    return entityManager
        .createQuery("from Tag where name =:tag_name", Tag.class)
        .setParameter("tag_name", name)
        .getResultStream()
        .findFirst();
  }

  @Override
  public Integer countTagsFromCertificateTag(BigInteger tagId) {
    return null;
  }
}
