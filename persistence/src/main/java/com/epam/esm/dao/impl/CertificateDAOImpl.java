package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.criteria.SearchCriteria;
import com.epam.esm.dao.mapper.CertificateMapper;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
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
@Component
public class CertificateDAOImpl implements CertificateDAO {
  private final JdbcTemplate jdbcTemplate;

  private static final String SQL_FIND_ALL =
      "SELECT id, name, description, price, duration,"
          + " create_date, last_update_date FROM gifts.certificate";
  private static final String SQL_FIND_ALL_BY_TAG_ID =
      SQL_FIND_ALL
          + " JOIN gifts.certificate_tag ON "
          + "gifts.certificate.id = gifts.certificate_tag.certificate_id"
          + " where gifts.certificate_tag.tag_id=?";
  private static final String SQL_FIND_BY_ID = SQL_FIND_ALL + " WHERE id=?";
  private static final String SQL_ADD_CERTIFICATE =
      "INSERT INTO gifts.certificate"
          + "(name, description, price, duration, create_date, last_update_date)"
          + " values(?, ?, ?, ?, ?, ?)";
  private static final String SQL_UPDATE =
      "UPDATE gifts.certificate SET "
          + "name=?, description=?, price=?, duration=?,"
          + " last_update_date=? where id=?";
  private static final String SQL_DELETE = "DELETE FROM gifts.certificate WHERE id=?";
  private static final String SQL_FIND_ALL_BY_TAG_NAME =
      "SELECT certificate.id, certificate.name, description, price, duration,"
          + " create_date, last_update_date FROM gifts.certificate"
          + " JOIN gifts.certificate_tag ON gifts.certificate.id = gifts.certificate_tag.certificate_id"
          + " JOIN gifts.tag ON gifts.certificate_tag.tag_id = gifts.tag.id WHERE tag.name = ?";
  private static final String SQL_ADD_TAG = "INSERT INTO gifts.tag(name) values(?)";
  private static final String SQL_FIND_TAG_BY_NAME =
      "SELECT id, name FROM gifts.tag WHERE name = ?";
  private static final String SQL_FIND_TAG_BY_ID = "SELECT id, name FROM gifts.tag WHERE id=?";
  private static final String SQL_ADD_TAG_CERTIFICATE =
      "INSERT INTO gifts.certificate_tag(certificate_id, tag_id) VALUES(?, ?)";
  private static final String SQL_FIND_TAGS_BY_CERTIFICATE_ID =
      "SELECT id, name FROM gifts.tag JOIN gifts.certificate_tag ON tag.id = certificate_tag.tag_id"
          + " WHERE certificate_id=?";

  /**
   * Constructor
   *
   * @param jdbcTemplate {@link org.springframework.jdbc.core.JdbcTemplate}
   */
  @Autowired
  public CertificateDAOImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Collection<Certificate> findByCriteria(SearchCriteria criteria) {
    final String query = criteria.getQuery();
    return jdbcTemplate.query(query, new CertificateMapper());
  }
/*
  @Override
  @Transactional
  public Certificate addCertificateWithTags(Certificate certificate, Collection<Tag> tags) {
    final Certificate addedCertificate = add(certificate);
    for (Tag tag : tags) {
      Optional<Tag> tagFromDb = Optional.empty();
      if (tag.getId() != null) {
        tagFromDb = getTagById(tag.getId());
      }
      if (tagFromDb.isEmpty()) {
        tagFromDb = getTagByName(tag);
      }
      if (tagFromDb.isPresent()) {
        addCertificateTag(addedCertificate.getId(), tagFromDb.get().getId());
      } else {
        final BigInteger tagId = addTag(tag);
        addCertificateTag(addedCertificate.getId(), tagId);
      }
    }
    return addedCertificate;
  }*/

  @Override
  public void addCertificateTag(BigInteger certificateId, BigInteger tagId) {
    jdbcTemplate.update(SQL_ADD_TAG_CERTIFICATE, certificateId.longValue(), tagId.longValue());
  }
  /**
   * Find certificate by id method
   *
   * @param id id of the certificate
   * @return certificate or null if certificate not found
   */
  @Override
  public Certificate findById(BigInteger id) throws EntityNotFoundException {
    return jdbcTemplate.query(SQL_FIND_BY_ID, new CertificateMapper(), id).stream()
        .findAny()
        .orElseThrow(
            () -> new EntityNotFoundException("Certificate with id : " + id + " not found"));
  }


  /**
   * Add certificate in database method
   *
   * @param certificate certificate entity
   * @return count of added rows
   */
  @Override
  public Certificate add(Certificate certificate) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(
        con -> {
          final PreparedStatement st =
              con.prepareStatement(SQL_ADD_CERTIFICATE, Statement.RETURN_GENERATED_KEYS);
          st.setString(1, certificate.getName());
          st.setString(2, certificate.getDescription());
          st.setBigDecimal(3, certificate.getPrice());
          st.setInt(4, certificate.getDuration());
          st.setTimestamp(5, Timestamp.valueOf(certificate.getCreateDate()));
          st.setTimestamp(6, Timestamp.valueOf(certificate.getLastUpdateDate()));
          return st;
        },
        keyHolder);
    certificate.setId(BigInteger.valueOf(keyHolder.getKey().longValue()));
    return certificate;
  }

  /**
   * Update certificate by id method
   *
   * @param id id of the certificate to update //* @param certificate certificate to update
   * @return count of added rows
   */
  @Override
  public void update(BigInteger id, Certificate certificate) throws EntityNotFoundException {
    final int result =
        jdbcTemplate.update(
            SQL_UPDATE,
            certificate.getName(),
            certificate.getDescription(),
            certificate.getPrice(),
            certificate.getDuration(),
            certificate.getLastUpdateDate(),
            id.longValue());
    if (result <= 0) {
      throw new EntityNotFoundException("Certificate with id : " + id + " not founnd");
    }
  }

  /**
   * Delete certificate by id method
   *
   * @param id id of the certificate to delete
   * @return count of deleted rows
   */
  @Override
  public void delete(BigInteger id) throws EntityNotFoundException {
    if (jdbcTemplate.update(SQL_DELETE, id.longValue()) <= 0) {
      throw new EntityNotFoundException("Certificate with id : " + id + " not found");
    }
  }
/*
//TODO Implement this logic in service
  private BigInteger addTag(Tag tag) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(con -> {
      final PreparedStatement st = con.prepareStatement(SQL_ADD_TAG, Statement.RETURN_GENERATED_KEYS);
      st.setString(1, tag.getName());
      return st;
    }, keyHolder);
    return BigInteger.valueOf(keyHolder.getKey().longValue());
  }

  private Optional<Tag> getTagByName(Tag tag) {
    return jdbcTemplate
        .query(SQL_FIND_TAG_BY_NAME, new BeanPropertyRowMapper<>(Tag.class), tag.getName())
        .stream()
        .findAny();
  }

  private Optional<Tag> getTagById(BigInteger id) {
    return jdbcTemplate
        .query(SQL_FIND_TAG_BY_ID, new BeanPropertyRowMapper<>(Tag.class), id)
        .stream()
        .findAny();
  }*/
}
