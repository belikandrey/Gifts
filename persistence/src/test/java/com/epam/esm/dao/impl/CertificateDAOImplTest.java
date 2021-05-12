package com.epam.esm.dao.impl;

import com.epam.esm.config.DaoTestConfig;
import com.epam.esm.dao.criteria.impl.CertificateSearchCriteria;
import com.epam.esm.dao.pagination.PaginationSetting;
import com.epam.esm.entity.Certificate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = DaoTestConfig.class)
@Transactional
class CertificateDAOImplTest {
  private CertificateDAOImpl certificateDAO;
  private Certificate certificate =
      new Certificate(
          BigInteger.ONE,
          "NewName",
          "Description ",
          BigDecimal.ONE,
          10,
          LocalDateTime.now(),
          LocalDateTime.now());

  @Autowired
  public CertificateDAOImplTest(CertificateDAOImpl certificateDAO) {
    this.certificateDAO = certificateDAO;
  }

  @Test
  public void findByCriteriaTest() {
    Map<String, Object> params = new HashMap<>();
    params.put("name", "Ap");
    params.put("description", "Certificate for one");
    CertificateSearchCriteria certificateSearchCriteria = new CertificateSearchCriteria(params);
    final Collection<Certificate> certificates =
        certificateDAO.findByCriteria(certificateSearchCriteria, PaginationSetting.getInstance(10, 1));
    assertFalse(certificates.isEmpty());
    assertEquals(1, certificates.size());
    final Optional<Certificate> certificate =
        certificates.stream().filter((p) -> p.getName().equalsIgnoreCase("Apple.com")).findAny();
    assertTrue(certificate.isPresent());
  }

  @Test
  public void saveTest() {
    certificate.setId(null);
    final Certificate saved = certificateDAO.save(certificate);
    assertNotNull(saved);
    assertNotNull(saved.getId());
    assertEquals(certificate.getName(), saved.getName());
  }

  @Test
  public void updateTest() {
    final Certificate updated = certificateDAO.update(certificate);
    assertNotNull(updated);
    assertEquals(certificate.getName(), updated.getName());
    final Optional<Certificate> certificateOptional = certificateDAO.findById(BigInteger.ONE);
    assertTrue(certificateOptional.isPresent());
    assertEquals(certificate.getName(), certificateOptional.get().getName());
  }

  @Test
  public void deleteByIdTest() {
    assertDoesNotThrow(() -> certificateDAO.deleteById(BigInteger.ONE));
  }
}
