package com.epam.esm.service.impl;

import com.epam.esm.config.TestConfig;
import com.epam.esm.dao.impl.CertificateDAO;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class CertificateServiceTest {

  @Autowired private CertificateService certificateService;

  @Autowired private CertificateDAO certificateDAO;

  @Autowired private CertificateConverter certificateConverter;

  @Autowired
  @Qualifier("tagServiceMock")
  private TagService tagService;

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
    when(certificateDAO.findAll()).thenReturn(certificates);
    final Collection<CertificateDTO> certificateDTOS = certificateService.findAll();
    assertNotNull(certificateDTOS);
    assertFalse(certificateDTOS.isEmpty());
    final long count = certificateDTOS.size();
    assertEquals(count, 2);
  }

  // Mock of certificate dao return list of certificates by id. Expected list of certificates
  @Test
  public void findAllByTagIdTest() {
    final List<Certificate> certificates = List.of(firstCertificate, secondCertificate);
    when(certificateConverter.convert(any(Certificate.class))).thenReturn(new CertificateDTO());
    when(certificateDAO.findAll(any(BigInteger.class))).thenReturn(certificates);
    final Collection<CertificateDTO> certificateDTOS = certificateService.findAll(FIRST_ID);
    assertNotNull(certificateDTOS);
    assertFalse(certificateDTOS.isEmpty());
    final int count = certificateDTOS.size();
    assertEquals(count, 2);
  }

  // Mock of certificate dao return list of certificates by params. Expected collection of
  // certificate dto
  @Test
  public void findAllWithTagsByParamsTest() {
    final List<Certificate> certificates = List.of(firstCertificate, secondCertificate);
    when(tagService.findAll(FIRST_ID)).thenReturn(Set.of(new TagDTO(BigInteger.ONE, TAG_NAME)));
    when(certificateDAO.findAll(FIRST_NAME, TAG_NAME, DESCRIPTION, "ASC", null))
        .thenReturn(certificates);
    when(certificateConverter.convert(any(Certificate.class)))
        .thenReturn(
            new CertificateDTO(
                FIRST_ID, FIRST_NAME, DESCRIPTION, PRICE, DURATION, CREATE_DATE, LAST_UPDATE_DATE));
    final Collection<CertificateDTO> certificateDTOS =
        certificateService.findAll(FIRST_NAME, TAG_NAME, DESCRIPTION, "ASC", null);
    assertNotNull(certificateDTOS);
    assertFalse(certificateDTOS.isEmpty());
    assertEquals(certificates.size(), certificateDTOS.size());
  }

  // Mock of certificate dao return one certificate. Expected one certificate dto
  @Test
  public void findByIdTest() {
    when(certificateDAO.find(any(BigInteger.class))).thenReturn(firstCertificate);
    when(certificateConverter.convert(any(Certificate.class))).thenReturn(new CertificateDTO());
    final CertificateDTO certificateDTO = certificateService.find(FIRST_ID);
    assertNotNull(certificateDTO);
  }

  // Mock of certificate dao return positive update value. Expected true
  @Test
  public void deleteTest() {
    when(certificateDAO.delete(any())).thenReturn(DAO_UPDATE_RETURN_VALUE);
    assertTrue(certificateService.delete(any()));
  }

  // Mock of certificate dao return positive update value. Expected similar value
  @Test
  public void updateTest() throws ValidatorException {
    when(certificateConverter.convert(any(CertificateDTO.class))).thenReturn(new Certificate());
    doNothing().when(certificateValidator).validate(any(Certificate.class));
    when(certificateDAO.update(any(), any())).thenReturn(DAO_UPDATE_RETURN_VALUE);
    assertEquals(
        certificateService.update(any(BigInteger.class), new CertificateDTO()),
        DAO_UPDATE_RETURN_VALUE);
  }

  // Mock of certificate dao return positive number. Expected not null certificate dto
  @Test
  public void addTest() throws ValidatorException {
    when(certificateConverter.convert(any(CertificateDTO.class))).thenReturn(new Certificate());
    when(certificateDAO.add(any(Certificate.class))).thenReturn(DAO_UPDATE_RETURN_VALUE);
    final CertificateDTO added = certificateService.add(new CertificateDTO());
    assertNotNull(added);
  }
}
