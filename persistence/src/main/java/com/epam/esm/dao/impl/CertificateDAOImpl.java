package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.criteria.SearchCriteria;
import com.epam.esm.dao.mapper.CertificateMapper;
import com.epam.esm.entity.Certificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
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
  private final JdbcTemplate jdbcTemplate;
  private final CertificateMapper certificateMapper;
  private static final String SQL_FIND_ALL =
      "SELECT id, name, description, price, duration,"
          + " create_date, last_update_date FROM gifts.certificate";
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
  private static final String SQL_ADD_TAG_CERTIFICATE =
      "INSERT INTO gifts.certificate_tag(certificate_id, tag_id) VALUES(?, ?)";
  private static final String SQL_DELETE_TAG_CERTIFICATE =
      "DELETE FROM gifts.certificate_tag WHERE certificate_id=? AND tag_id=?";

  /**
   * Constructor
   *
   * @param jdbcTemplate {@link org.springframework.jdbc.core.JdbcTemplate}
   * @param certificateMapper {@link CertificateMapper}
   */
  @Autowired
  public CertificateDAOImpl(JdbcTemplate jdbcTemplate, CertificateMapper certificateMapper) {
    this.jdbcTemplate = jdbcTemplate;
    this.certificateMapper = certificateMapper;
  }

  /**
   * Find by criteria method
   *
   * @param criteria {@link SearchCriteria} for searching by
   * @return {@link Collection} of certificates
   */
  @Override
  public Collection<Certificate> findByCriteria(SearchCriteria criteria) {
    final String query = criteria.getQuery();
    return jdbcTemplate.query(query, certificateMapper);
  }

  /**
   * Add certificate id and tag id in database
   *
   * @param certificateId id of certificate
   * @param tagId id of tag
   * @return true if added, false in another way
   */
  @Override
  public boolean addCertificateTag(BigInteger certificateId, BigInteger tagId) {
    return jdbcTemplate.update(
            SQL_ADD_TAG_CERTIFICATE, certificateId.longValue(), tagId.longValue())
        > 0;
  }

  @Override
  public boolean deleteCertificateTag(BigInteger certificateId, BigInteger tagId) {
    return jdbcTemplate.update(
            SQL_DELETE_TAG_CERTIFICATE, certificateId.longValue(), tagId.longValue())
        > 0;
  }
  /**
   * Find certificate by id method
   *
   * @param id id of the certificate
   * @return {@link Optional} of certificate
   */
  @Override
  public Optional<Certificate> findById(BigInteger id) {
    return jdbcTemplate.query(SQL_FIND_BY_ID, certificateMapper, id).stream().findAny();
  }

  /**
   * Add certificate in database method
   *
   * @param certificate certificate entity
   * @return added certificate
   */
  @Override
  public Certificate add(Certificate certificate) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(
        con -> {
          PreparedStatement st =
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
   * @param id id of the certificate to update
   * @param certificate certificate to update
   * @return true if updated, false in another way
   */
  @Override
  public boolean update(BigInteger id, Certificate certificate) {
    return jdbcTemplate.update(
            SQL_UPDATE,
            certificate.getName(),
            certificate.getDescription(),
            certificate.getPrice(),
            certificate.getDuration(),
            certificate.getLastUpdateDate(),
            id.longValue())
        > 0;
  }

  /**
   * Delete certificate by id method
   *
   * @param id id of the certificate to delete
   * @return true if deleted, false in another way
   */
  @Override
  public boolean delete(BigInteger id) {
    return jdbcTemplate.update(SQL_DELETE, id.longValue()) > 0;
  }
}
