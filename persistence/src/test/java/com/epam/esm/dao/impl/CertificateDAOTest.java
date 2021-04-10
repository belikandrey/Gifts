package com.epam.esm.dao.impl;

import com.epam.esm.entity.Certificate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class CertificateDAOTest {
  /*private static JdbcTemplate jdbcTemplate;
  private static CertificateDAOImpl certificateDAO;
  private static EmbeddedDatabase embeddedDatabase;
  private static final String CERTIFICATE_NAME = "Cars.by";
  private static final BigInteger TAG_ID = BigInteger.ONE;
  private static final String TAG_NAME = "#cool";
  private static final BigInteger CERTIFICATE_ID = BigInteger.TWO;
  private static final BigInteger SECOND_CERTIFICATE_ID = BigInteger.TWO;
  private static final BigInteger THIRD_CERTIFICATE_ID = BigInteger.valueOf(6);
  private static final String SECOND_CERTIFICATE_NAME = "SecondName";
  private Certificate certificate =
      new Certificate(
          CERTIFICATE_ID,
          CERTIFICATE_NAME,
          "Some description",
          BigDecimal.TEN,
          10,
          LocalDateTime.now(),
          LocalDateTime.now());

  public static DataSource dataSource() {
    embeddedDatabase =
        new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .addScript("sql/init_h2.sql")
            .build();
    return embeddedDatabase;
  }

  @BeforeAll
  public static void init() {
    jdbcTemplate = new JdbcTemplate(dataSource());
    certificateDAO = new CertificateDAOImpl(jdbcTemplate);
  }

  @Test
  void findAllTest() {
    final Collection<Certificate> certificates = certificateDAO.findAll();
    assertNotNull(certificates);
    assertFalse(certificates.isEmpty());
    assertTrue(certificates.stream().anyMatch((p) -> p.getName().equals(CERTIFICATE_NAME)));
  }

  @Test
  void findAllByTagNameTest() {
    final Collection<Certificate> certificates = certificateDAO.findAll(TAG_NAME);
    assertNotNull(certificates);
    assertFalse(certificates.isEmpty());
    assertTrue(certificates.stream().anyMatch((p) -> p.getName().equals(CERTIFICATE_NAME)));
  }

  @Test
  void findAllByTagIdTest() {
    final Collection<Certificate> certificates = certificateDAO.findAll(TAG_ID);
    assertNotNull(certificates);
    assertFalse(certificates.isEmpty());
    assertEquals(certificates.size(), 2);
    assertTrue(certificates.stream().anyMatch((p) -> p.getName().equals(CERTIFICATE_NAME)));
  }

  @Test
  void find() {
    final Certificate certificate = certificateDAO.find(CERTIFICATE_ID);
    assertNotNull(certificate);
    assertEquals(certificate.getId(), CERTIFICATE_ID);
    assertEquals(certificate.getName(), CERTIFICATE_NAME);
  }

  @Test
  void add() {
    assertTrue(certificateDAO.add(certificate) > 0);
  }

  @Test
  void update() {
    assertTrue(certificateDAO.update(SECOND_CERTIFICATE_ID, certificate) > 0);
  }

  @Test
  void delete() {
    assertTrue(certificateDAO.delete(THIRD_CERTIFICATE_ID) > 0);
  }

  @AfterAll
  static void clean() {
    embeddedDatabase.shutdown();
  }*/
}
