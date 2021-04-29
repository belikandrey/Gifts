package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractGiftDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

/**
 * Class that interacts with the database
 *
 * @version 1.0
 * @author Andrey Belik
 * @see com.epam.esm.dao.AbstractGiftDAO
 * @see com.epam.esm.dao.TagDAO
 */
@Repository
public class TagDAOImpl extends AbstractGiftDAO<Tag> implements TagDAO {

  /** The constant FIND_BY_NAME. */
  private static final String FIND_BY_NAME = "from Tag where name =:tag_name";

  /** The constant FIND_MOST_POPULAR_TAG. */
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

  /** The constant FIND_COUNT_OF_TAG_USAGES. */
  private static final String FIND_COUNT_OF_TAG_USAGES =
      "select count(certificate_id) from certificate_tag where tag_id=:tag_id group by (tag_id)";

  /** Instantiates a new Tag dao. */
  public TagDAOImpl() {
    setClazz(Tag.class);
  }

  /**
   * Save tag.
   *
   * @param entity the {@link Tag}
   * @return the {@link Tag}
   */
  @Override
  public Tag save(Tag entity) {
    getEntityManager().persist(entity);
    return entity;
  }

  /**
   * Delete by id.
   *
   * @param id the id {@link BigInteger}
   */
  @Override
  public void deleteById(BigInteger id) {
    final Tag entity = getEntityManager().find(Tag.class, id);
    getEntityManager().remove(entity);
  }

  /**
   * Find tag by name optional.
   *
   * @param name the name
   * @return the {@link Optional} of {@link Tag}
   */
  @Override
  public Optional<Tag> findTagByName(String name) {
    return getEntityManager()
        .createQuery(FIND_BY_NAME)
        .setParameter("tag_name", name)
        .getResultStream()
        .findAny();
  }

  /**
   * Find most popular tag optional.
   *
   * @return the {@link Optional} of {@link Tag}
   */
  @Override
  public Optional<Tag> findMostPopularTag() {
    return getEntityManager()
        .createNativeQuery(FIND_MOST_POPULAR_TAG, Tag.class)
        .getResultStream()
        .findAny();
  }

  /**
   * Is tag used boolean.
   *
   * @param tagId the tag id
   * @return true if tag is used in certificates, otherwise false
   */
  @Override
  public boolean isTagUsed(BigInteger tagId) {
    final Optional<Object> countOfTags =
        getEntityManager()
            .createNativeQuery(FIND_COUNT_OF_TAG_USAGES)
            .setParameter("tag_id", tagId)
            .getResultStream()
            .findAny();
    return countOfTags.isPresent();
  }
}
