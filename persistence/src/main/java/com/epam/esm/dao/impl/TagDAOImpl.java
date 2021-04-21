package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Tag;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
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

  @Autowired
  private EntityManager entityManager;

  @Override
  public Collection<Tag> findAll() {
//    Session session = entityManager.unwrap(Session.class);
//    Query query = session.createQuery("from com.epam.esm.entity.Tag");
//    final List<Tag> tags = query.getResultList();
    final Query query = entityManager.createQuery("from Tag");
    final List<Tag> resultList = query.getResultList();
    return resultList;
  }

  @Override
  public Optional<Tag> findById(BigInteger id) {
    return null;
  }

  @Override
  public Tag add(Tag tag) {
    return null;
  }

  @Override
  public boolean update(BigInteger id, Tag tag) {
    return false;
  }

  @Override
  public boolean delete(BigInteger id) {
    return false;
  }


  @Override
  public Set<Tag> findTagsByCertificateId(BigInteger certificateId) {
    return null;
  }

  @Override
  public boolean isAlreadyExist(Tag tag) {
    return false;
  }

  @Override
  public Optional<Tag> findTagByName(String name) {
    return Optional.empty();
  }

  @Override
  public Integer countTagsFromCertificateTag(BigInteger tagId) {
    return null;
  }
  /*private final JdbcTemplate jdbcTemplate;
  private final RowMapper<Tag> rowMapper;

  private static final String SQL_FIND_ALL = "SELECT id, name FROM gifts.tag";
  private static final String SQL_FIND_BY_ID = SQL_FIND_ALL + " WHERE id = ?";
  private static final String SQL_ADD = "INSERT INTO gifts.tag(name) VALUES(?)";
  private static final String SQL_DELETE = "DELETE FROM gifts.tag WHERE id=?";
  private static final String SQL_FIND_BY_NAME = "SELECT id, name FROM gifts.tag WHERE name = ?";
  private static final String SQL_FIND_TAGS_BY_CERTIFICATE_ID =
      "SELECT id, name FROM gifts.tag JOIN gifts.certificate_tag ON tag.id = certificate_tag.tag_id"
          + " WHERE certificate_id=?";
  private static final String SQL_COUNT_TAGS_FROM_CERTIFICATE_TAG =
      "SELECT COUNT(tag_id) AS count FROM gifts.certificate_tag "
          + "JOIN gifts.tag ON tag.id = certificate_tag.tag_id "
          + "WHERE tag_id = ?";

  /**
   * Constructor
   *
   * @param jdbcTemplate {@link org.springframework.jdbc.core.JdbcTemplate}
   * @param rowMapper {@link RowMapper}
   */
//  @Autowired
//  public TagDAOImpl(JdbcTemplate jdbcTemplate, RowMapper<Tag> rowMapper) {
//    this.jdbcTemplate = jdbcTemplate;
//    this.rowMapper = rowMapper;
//  }
//
//  /**
//   * Find tag by id method
//   *
//   * @param id id of the tag
//   * @return {@link Optional} of tag
//   */
//  @Override
//  public Optional<Tag> findById(BigInteger id) {
//    return jdbcTemplate.query(SQL_FIND_BY_ID, rowMapper, id.longValue()).stream().findAny();
//  }
//
//  /**
//   * Add tag in database method
//   *
//   * @param tag tag for add
//   * @return null if tag exists, tag in another way
//   */
//  @Override
//  public Tag add(Tag tag) {
//    KeyHolder keyHolder = new GeneratedKeyHolder();
//    if (isAlreadyExist(tag)) {
//      return null;
//    }
//    jdbcTemplate.update(
//        connection -> {
//          PreparedStatement statement =
//              connection.prepareStatement(SQL_ADD, Statement.RETURN_GENERATED_KEYS);
//          statement.setString(1, tag.getName());
//          return statement;
//        },
//        keyHolder);
//    final BigInteger id = BigInteger.valueOf(keyHolder.getKey().longValue());
//    tag.setId(id);
//    return tag;
//  }
//
//  /**
//   * Update tag by id method
//   *
//   * <p>Unsupported
//   *
//   * @throws UnsupportedOperationException in any way
//   */
//  @Override
//  public boolean update(BigInteger id, Tag tag) {
//    throw new UnsupportedOperationException();
//  }
//
//  /**
//   * Delete tag by id method
//   *
//   * @param id id of the tag to delete
//   * @return true if deleted, false in another way
//   */
//  @Override
//  public boolean delete(BigInteger id) {
//    return jdbcTemplate.update(SQL_DELETE, id.longValue()) > 0;
//  }
//
//  /**
//   * Check is tag already exist method
//   *
//   * @param tag tag for check
//   * @return true if exist, false in another way
//   */
//  @Override
//  public boolean isAlreadyExist(Tag tag) {
//    Optional<Tag> tagOptional = Optional.empty();
//    tagOptional = tag.getId() != null ? findById(tag.getId()) : tagOptional;
//    tagOptional =
//        tagOptional.isEmpty() && tag.getName() != null ? findTagByName(tag.getName()) : tagOptional;
//    return tagOptional.isPresent();
//  }
//
//  /**
//   * Find tag by name method
//   *
//   * @param name name of tag
//   * @return {@link Optional} of tag
//   */
//  @Override
//  public Optional<Tag> findTagByName(String name) {
//    return jdbcTemplate.query(SQL_FIND_BY_NAME, rowMapper, name).stream().findAny();
//  }
//
//  /**
//   * Find all tags method
//   *
//   * @return {@link Collection} of tags
//   */
//  @Override
//  public Collection<Tag> findAll() {
//    return jdbcTemplate.query(SQL_FIND_ALL, rowMapper);
//  }
//
//  /**
//   * Find tags by certificate id method
//   *
//   * @param certificateId id of certificate
//   * @return {@link Set} of tags
//   */
//  @Override
//  public Set<Tag> findTagsByCertificateId(BigInteger certificateId) {
//    return new HashSet<>(
//        jdbcTemplate.query(SQL_FIND_TAGS_BY_CERTIFICATE_ID, rowMapper, certificateId));
//  }
//
//  @Override
//  public Integer countTagsFromCertificateTag(BigInteger tagId) {
//    return jdbcTemplate
//        .query(
//            SQL_COUNT_TAGS_FROM_CERTIFICATE_TAG,
//                (rs, rowNum) -> rs.getInt("count"), tagId)
//        .get(0);
//  }
}
