package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EmptyKeyHolderException;
import com.epam.esm.exception.EntityAlreadyExistException;
import com.epam.esm.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Class that interacts with the database
 *
 * @author Andrey Belik
 * @version 1.0
 */
@Component
public class TagDAOImpl implements TagDAO {
  private final JdbcTemplate jdbcTemplate;

  private static final String SQL_FIND_ALL = "SELECT id, name FROM gifts.tag";
  private static final String SQL_FIND_ALL_BY_CERTIFICATE_ID =
      SQL_FIND_ALL
          + " JOIN gifts.certificate_tag ON tag.id = certificate_tag.tag_id"
          + " WHERE certificate_id=?";
  private static final String SQL_FIND_BY_ID = SQL_FIND_ALL + " WHERE id = ?";
  private static final String SQL_ADD = "INSERT INTO gifts.tag(name) VALUES(?)";
  private static final String SQL_DELETE = "DELETE FROM gifts.tag WHERE id=?";
  private static final String SQL_FIND_BY_NAME = "SELECT id, name FROM gifts.tag WHERE name=?";
  private static final String SQL_ADD_TAG_CERTIFICATE =
      "INSERT INTO gifts.certificate_tag(certificate_id, tag_id) " + "VALUES(?, ?)";
  private static final String SQL_FIND_LAST_UPDATE_ID = "SELECT LAST_INSERT_ID() AS id";

  /**
   * Constructor
   *
   * @param jdbcTemplate {@link org.springframework.jdbc.core.JdbcTemplate}
   */
  @Autowired
  public TagDAOImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }
  /*
  /**
   * Add tags for certificate in database method
   *
   * @param tags {@link java.util.Set} of tags
   * @param certificateId certificate id
   */
  /*public void add(Set<Tag> tags, BigInteger certificateId) {
    for (Tag tag : tags) {
      add(tag);
      addCertificateTag(certificateId, find(tag.getName()).getId());
    }
  }

  private void addCertificateTag(BigInteger certificateId, BigInteger tagId) {
    jdbcTemplate.update(SQL_ADD_TAG_CERTIFICATE, certificateId.longValue(), tagId.longValue());
  }*/

  /**
   * Find tag by id method
   *
   * @param id id of the tag
   * @return tag or null if tag not found
   */
  @Override
  public Tag findById(BigInteger id) throws EntityNotFoundException {
    return jdbcTemplate
        .query(SQL_FIND_BY_ID, new BeanPropertyRowMapper<>(Tag.class), id.longValue())
        .stream()
        .findAny()
        .orElseThrow(() -> new EntityNotFoundException("Tag with id : " + id + " not found"));
  }

  /**
   * Add tag in database method
   *
   * @param tag tag for add
   * @return count of added rows
   */
  @Override
  public Tag add(Tag tag) throws EntityAlreadyExistException {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    if (isAlreadyExist(tag)) {
      throw new EntityAlreadyExistException("Tag with name : " + tag.getName() + " already exist");
    }
    jdbcTemplate.update(
        connection -> {
          PreparedStatement statement =
              connection.prepareStatement(SQL_ADD, Statement.RETURN_GENERATED_KEYS);
          statement.setString(1, tag.getName());
          return statement;
        },
        keyHolder);
    final BigInteger id = BigInteger.valueOf(keyHolder.getKey().longValue());
    tag.setId(id);
    return tag;
  }

  /**
   * Update tag by id method
   *
   * @param id id of the tag to update
   * @param tag tag to update
   * @return count of added rows
   */
  @Override
  public void update(BigInteger id, Tag tag) {
    throw new UnsupportedOperationException();
  }

  /**
   * Delete tag by id method
   *
   * @param id id of the tag to delete
   * @return count of deleted rows
   */
  @Override
  public void delete(BigInteger id) throws EntityNotFoundException {
    if (jdbcTemplate.update(SQL_DELETE, id.longValue()) <= 0) {
      throw new EntityNotFoundException("Tag with id " + id + " not found");
    }
  }

  @Override
  public boolean isAlreadyExist(Tag tag) {
    final Optional<Tag> tagOptional =
        jdbcTemplate
            .query(SQL_FIND_BY_NAME, new BeanPropertyRowMapper<>(Tag.class), tag.getName())
            .stream()
            .findAny();
    return tagOptional.isPresent() ? true : false;
  }

  @Override
  public Collection<Tag> findAll() {
    return jdbcTemplate.query(SQL_FIND_ALL, new BeanPropertyRowMapper<>(Tag.class));
  }
}
