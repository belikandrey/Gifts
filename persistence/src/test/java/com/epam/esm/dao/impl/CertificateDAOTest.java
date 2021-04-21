package com.epam.esm.dao.impl;

import com.epam.esm.dao.criteria.impl.CertificateSearchCriteria;
import com.epam.esm.dao.mapper.CertificateMapper;
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
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CertificateDAOTest {
//  private static JdbcTemplate jdbcTemplate;
//  private static CertificateMapper certificateMapper = new CertificateMapper();
//  private static CertificateDAOImpl certificateDAO;
//  private static EmbeddedDatabase embeddedDatabase;
//  private static final String CERTIFICATE_NAME = "Cars.by";
//  private static final String TAG_NAME = "#cool";
//  private static final BigInteger CERTIFICATE_ID = BigInteger.TWO;
//  private static final BigInteger SECOND_CERTIFICATE_ID = BigInteger.TWO;
//  private static final BigInteger THIRD_CERTIFICATE_ID = BigInteger.valueOf(6);
//  private static final String SECOND_CERTIFICATE_NAME = "SecondName";
//  private Certificate certificate =
//      new Certificate(
//          CERTIFICATE_ID,
//          CERTIFICATE_NAME,
//          "Some description",
//          BigDecimal.TEN,
//          10,
//          LocalDateTime.now(),
//          LocalDateTime.now());
//
//  public static DataSource dataSource() {
//    embeddedDatabase =
//        new EmbeddedDatabaseBuilder()
//            .setType(EmbeddedDatabaseType.H2)
//            .addScript("sql/init_h2.sql")
//            .build();
//    return embeddedDatabase;
//  }
//
//  @BeforeAll
//  public static void init() {
//    jdbcTemplate = new JdbcTemplate(dataSource());
//    certificateDAO = new CertificateDAOImpl(jdbcTemplate, certificateMapper);
//  }
//
//  @Test
//  void updateTest() {
//    assertTrue(certificateDAO.update(SECOND_CERTIFICATE_ID, certificate));
//  }
//
//  @Test
//  void deleteTest() {
//    assertTrue(certificateDAO.delete(THIRD_CERTIFICATE_ID));
//  }
//
//  @Test
//  void addTest() {
//    final Certificate addedCertificate = certificateDAO.add(certificate);
//    assertEquals(addedCertificate.getName(), certificate.getName());
//    assertEquals(addedCertificate.getDescription(), certificate.getDescription());
//    assertEquals(addedCertificate.getDuration(), certificate.getDuration());
//    assertEquals(addedCertificate.getPrice(), certificate.getPrice());
//  }
//
//  @Test
//  void findByIdTest() {
//    final Certificate certificate = certificateDAO.findById(SECOND_CERTIFICATE_ID).orElse(null);
//    assertNotNull(certificate);
//    assertEquals(certificate.getId(), SECOND_CERTIFICATE_ID);
//  }
//
//  @Test
//  void findByCriteria() {
//    Map<String, String> params = new HashMap<>();
//    params.put("tagName", TAG_NAME);
//    final Collection<Certificate> certificates =
//        certificateDAO.findByCriteria(new CertificateSearchCriteria(params));
//    assertNotNull(certificates);
//    assertFalse(certificates.isEmpty());
//  }
//
//  @AfterAll
//  static void clean() {
//    embeddedDatabase.shutdown();
//  }
}
