package com.epam.esm.service.impl;

import com.epam.esm.config.TestConfig;
import com.epam.esm.dao.impl.CertificateDAOImpl;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.converter.impl.CertificateConverter;
import com.epam.esm.entity.Certificate;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.validator.impl.CertificateValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class CertificateServiceTest {

  @Autowired private CertificateServiceImpl certificateService;

  @Autowired private CertificateDAOImpl certificateDAO;

  @Autowired private CertificateConverter certificateConverter;

  @Autowired
  @Qualifier("tagServiceMock")
  private TagServiceImpl tagService;

  @Autowired private CertificateValidator certificateValidator;

  private static final String FIRST_NAME = "Apple.com";
  private static final BigInteger FIRST_ID = BigInteger.ONE;
  private static final BigInteger SECOND_ID = BigInteger.TWO;
  private static final String SECOND_NAME = "OZ.com";
  private static final String DESCRIPTION = "Certificate description";
  private static final BigDecimal PRICE = BigDecimal.TEN;
  private static final int DURATION = 42;
  private static final String TAG_NAME = "#cool";
  private static final LocalDateTime CREATE_DATE = LocalDateTime.now();
  private static final LocalDateTime LAST_UPDATE_DATE = LocalDateTime.now();
  private static final int DAO_UPDATE_RETURN_VALUE = 3;
  private final Certificate firstCertificate =
      new Certificate(
          FIRST_ID, FIRST_NAME, DESCRIPTION, PRICE, DURATION, CREATE_DATE, LAST_UPDATE_DATE);
  private final Certificate secondCertificate =
      new Certificate(
          SECOND_ID, SECOND_NAME, DESCRIPTION, PRICE, DURATION, CREATE_DATE, LAST_UPDATE_DATE);

  // Mock of certificate Dao returns list of certificates. Expected list of certificates
  @Test
  public void findAllTest() {
    final List<Certificate> certificates = List.of(firstCertificate, secondCertificate);
    when(certificateConverter.convert(any(Certificate.class))).thenReturn(new CertificateDTO());
    when(certificateDAO.findByCriteria(any())).thenReturn(certificates);
    final Collection<CertificateDTO> certificateDTOS =
        certificateService.findAll(TAG_NAME, FIRST_NAME, DESCRIPTION, null, "asc");
    assertNotNull(certificateDTOS);
    assertFalse(certificateDTOS.isEmpty());
    final long count = certificateDTOS.size();
    assertEquals(count, 2);
  }

  @Test
  public void findByIdTest() {
    when(certificateDAO.findById(any(BigInteger.class))).thenReturn(Optional.of(firstCertificate));
    when(certificateConverter.convert(any(Certificate.class))).thenReturn(new CertificateDTO());
    final CertificateDTO certificateDTO = certificateService.findById(FIRST_ID);
    assertNotNull(certificateDTO);
  }

  @Test
  public void addTest() throws ValidatorException {
    when(certificateConverter.convert(any(CertificateDTO.class))).thenReturn(new Certificate());
    when(certificateConverter.convert(any(Certificate.class))).thenReturn(new CertificateDTO());
    when(certificateDAO.add(any(Certificate.class))).thenReturn(new Certificate());
    doNothing().when(certificateValidator).validate(any());
    final CertificateDTO added = certificateService.add(new CertificateDTO());
    assertNotNull(added);
  }

  @Test
  public void updateTest() throws ValidatorException {
    when(certificateConverter.convert(any(CertificateDTO.class))).thenReturn(new Certificate());
    doNothing().when(certificateValidator).validate(new Certificate());
    when(certificateDAO.update(any(), any())).thenReturn(true);
    when(certificateDAO.findById(any())).thenReturn(Optional.of(new Certificate()));
    when(certificateConverter.convert(any(Certificate.class))).thenReturn(new CertificateDTO());
    assertDoesNotThrow(() -> certificateService.update(BigInteger.TEN, new CertificateDTO()));
  }

  @Test
  public void deleteTest() {
    when(certificateDAO.delete(any())).thenReturn(true);
    assertDoesNotThrow(() -> certificateService.delete(any()));
  }
}
