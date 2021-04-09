package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDAO;
import com.epam.esm.dao.mapper.CertificateMapper;
import com.epam.esm.entity.Certificate;
import com.epam.esm.search.SearchQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Collection;

/**
 * Class that interacts with the database
 *
 * @author Andrey Belik
 * @version 1.0
 */
@Component
public class CertificateDAO implements AbstractDAO<Certificate, BigInteger> {
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
  private static final String SQL_ADD =
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

  /**
   * Constructor
   *
   * @param jdbcTemplate {@link org.springframework.jdbc.core.JdbcTemplate}
   */
  @Autowired
  public CertificateDAO(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  /**
   * Find all tags method method
   *
   * @return {@link java.util.Collection} of certificates
   */
  @Override
  public Collection<Certificate> findAll() {
    return jdbcTemplate.query(SQL_FIND_ALL, new CertificateMapper());
  }

  /**
   * Find all certificates by tag id method
   *
   * @param tagId id of the tag for find
   * @return {@link java.util.Collection} of certificates
   */
  @Override
  public Collection<Certificate> findAll(BigInteger tagId) {
    return jdbcTemplate.query(SQL_FIND_ALL_BY_TAG_ID, new CertificateMapper(), tagId.longValue());
  }

  /**
   * Find all certificates by params
   *
   * @param tagName name of the tag
   * @param name part of name of the certificate
   * @param description part of description of the certificate
   * @param sortName sort by name type(asc, desc)
   * @param sortDate sort by date type(asc, desc)
   * @return {@link java.util.Collection} of certificates by params
   * @see com.epam.esm.search.SearchQueryBuilder
   */
  public Collection<Certificate> findAll(
      String tagName, String name, String description, String sortName, String sortDate) {
    final String searchQuery = getSearchQuery(tagName, name, description, sortName, sortDate);
    return jdbcTemplate.query(searchQuery, new CertificateMapper());
  }

  /**
   * Find certificates by tag name method
   *
   * @param tagName name of tag
   * @return {@link java.util.Collection} of certificates
   */
  public Collection<Certificate> findAll(String tagName) {
    return jdbcTemplate.query(SQL_FIND_ALL_BY_TAG_NAME, new CertificateMapper(), tagName);
  }

  /**
   * Find certificate by id method
   *
   * @param id id of the certificate
   * @return certificate or null if certificate not found
   */
  @Override
  public Certificate find(BigInteger id) {
    return jdbcTemplate.query(SQL_FIND_BY_ID, new CertificateMapper(), id.longValue()).stream()
        .findAny()
        .orElse(null);
  }

  /**
   * Add certificate in database method
   *
   * @param certificate certificate entity
   * @return count of added rows
   */
  @Override
  public int add(Certificate certificate) {
    return jdbcTemplate.update(
        SQL_ADD,
        certificate.getName(),
        certificate.getDescription(),
        certificate.getPrice(),
        certificate.getDuration(),
        certificate.getCreateDate(),
        certificate.getLastUpdateDate());
  }

  /**
   * Update certificate by id method
   *
   * @param id id of the certificate to update
   * @param certificate certificate to update
   * @return count of added rows
   */
  @Override
  public int update(BigInteger id, Certificate certificate) {
    return jdbcTemplate.update(
        SQL_UPDATE,
        certificate.getName(),
        certificate.getDescription(),
        certificate.getPrice(),
        certificate.getDuration(),
        certificate.getLastUpdateDate(),
        id.longValue());
  }

  /**
   * Delete certificate by id method
   *
   * @param id id of the certificate to delete
   * @return count of deleted rows
   */
  @Override
  public int delete(BigInteger id) {
    return jdbcTemplate.update(SQL_DELETE, id.longValue());
  }

  private String getSearchQuery(
      String tagName, String name, String description, String sortName, String sortDate) {
    SearchQueryBuilder builder = new SearchQueryBuilder();
    builder = tagName != null && !tagName.isEmpty() ? builder.setTagName(tagName) : builder;
    builder = name != null && !name.isEmpty() ? builder.setName(name) : builder;
    builder =
        description != null && !description.isEmpty()
            ? builder.setDescription(description)
            : builder;
    builder =
        sortName != null && !sortName.isEmpty()
            ? builder.setSortByName(sortName)
            : sortDate != null && !sortDate.isEmpty() ? builder.setSortByDate(sortDate) : builder;
    return builder.build();
  }
}
