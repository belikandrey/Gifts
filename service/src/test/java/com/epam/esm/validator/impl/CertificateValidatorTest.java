package com.epam.esm.validator.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.exception.ValidatorException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CertificateValidatorTest {
  private static CertificateValidator validator;

  private static final String SMALL_CERTIFICATE_NAME = "Ab";
  private static final String BIG_CERTIFICATE_NAME = "Big certificate name. Very big name";
  private static final String NORMAL_CERTIFICATE_NAME = "Apple.com";
  private static final String NORMAL_CERTIFICATE_DESCRIPTION = "Certificate for Iphone X";
  private static final int NORMAL_CERTIFICATE_DURATION = 42;
  private static final BigDecimal NORMAL_CERTIFICATE_PRICE = BigDecimal.TEN;
  private static final int NEGATIVE_CERTIFICATE_DURATION = -42;
  private static final BigDecimal NEGATIVE_CERTIFICATE_PRICE = BigDecimal.valueOf(-42);

  private static final String NULL_CERTIFICATE_EXCEPTION_MESSAGE = "Certificate should be not null";
  private static final String EMPTY_CERTIFICATE_NAME_EXCEPTION_MESSAGE =
      "Certificate name should be not empty";
  private static final String SMALL_CERTIFICATE_NAME_EXCEPTION_MESSAGE =
      "Certificate name should be at least 3 characters";
  private static final String BIG_CERTIFICATE_NAME_EXCEPTION_MESSAGE =
      "Certificate name should be not more than 30 characters";
  private static final String EMPTY_CERTIFICATE_DESCRIPTION_EXCEPTION_MESSAGE =
      "Certificate description should be not empty";
  private static final String NEGATIVE_CERTIFICATE_DURATION_EXCEPTION_MESSAGE =
      "Certificate duration should be more than 0";
  private static final String NEGATIVE_CERTIFICATE_PRICE_EXCEPTION_MESSAGE =
      "Certificate price should be more than or equals 0";

  @BeforeAll
  public static void init() {
    validator = new CertificateValidator();
  }

  // Given certificate with right fields. Expected work without exception
  @Test
  public void rightCertificateTest() {
    Certificate rightCertificate =
        new Certificate(
            BigInteger.ONE,
            NORMAL_CERTIFICATE_NAME,
            NORMAL_CERTIFICATE_DESCRIPTION,
            NORMAL_CERTIFICATE_PRICE,
            NORMAL_CERTIFICATE_DURATION,
            LocalDateTime.now(),
            LocalDateTime.now());
    assertDoesNotThrow(() -> validator.validate(rightCertificate));
  }

  // Null given. Expected ValidatorException with message "Certificate should be not null"
  @Test
  public void nullCertificateTest() {
    final ValidatorException exception =
        assertThrows(ValidatorException.class, () -> validator.validate(null));
    assertEquals(exception.getMessages().get(0), NULL_CERTIFICATE_EXCEPTION_MESSAGE);
  }

  // Given certificate with empty name. Expected ValidationException with message "Certificate name
  // should be
  // not empty"
  @Test
  public void emptyCertificateNameTest() {
    Certificate certificate = new Certificate();
    final ValidatorException exception =
        assertThrows(ValidatorException.class, () -> validator.validate(certificate));
    assertTrue(exception.getMessages().contains(EMPTY_CERTIFICATE_NAME_EXCEPTION_MESSAGE));
  }

  // Given certificate with small size name. Expected ValidationException with message "Certificate
  // name
  // should be at least 3 characters"
  @Test
  public void smallCertificateNameTest() {
    Certificate certificate = new Certificate();
    certificate.setName(SMALL_CERTIFICATE_NAME);
    final ValidatorException exception =
        assertThrows(ValidatorException.class, () -> validator.validate(certificate));
    assertTrue(exception.getMessages().contains(SMALL_CERTIFICATE_NAME_EXCEPTION_MESSAGE));
  }

  // Given certificate with big size name. Expected ValidationException with message "Certificate
  // name should
  // be not more than 30 characters"
  @Test
  public void bigCertificateNameTest() {
    Certificate certificate = new Certificate();
    certificate.setName(BIG_CERTIFICATE_NAME);
    final ValidatorException exception =
        assertThrows(ValidatorException.class, () -> validator.validate(certificate));
    assertTrue(exception.getMessages().contains(BIG_CERTIFICATE_NAME_EXCEPTION_MESSAGE));
  }

  // Given certificate with empty duration. Expected ValidationException with message "Certificate
  // description should be not empty"
  @Test
  public void emptyCertificateDescriptionTest() {
    Certificate certificate = new Certificate();
    certificate.setName(NORMAL_CERTIFICATE_NAME);
    final ValidatorException exception =
        assertThrows(ValidatorException.class, () -> validator.validate(certificate));
    assertTrue(exception.getMessages().contains(EMPTY_CERTIFICATE_DESCRIPTION_EXCEPTION_MESSAGE));
  }

  // Given certificate with negative duration. Expected ValidationException with message
  // "Certificate duration should be more than 0"
  @Test
  public void negativeCertificateDurationTest() {
    Certificate certificate = new Certificate();
    certificate.setName(NORMAL_CERTIFICATE_NAME);
    certificate.setDescription(NORMAL_CERTIFICATE_DESCRIPTION);
    certificate.setDuration(NEGATIVE_CERTIFICATE_DURATION);
    final ValidatorException exception =
        assertThrows(ValidatorException.class, () -> validator.validate(certificate));
    assertTrue(exception.getMessages().contains(NEGATIVE_CERTIFICATE_DURATION_EXCEPTION_MESSAGE));
  }

  // Given certificate with negative price. Expected ValidationException with message "Certificate
  // price should be more than or equals 0"
  @Test
  public void negativeCertificatePriceTest() {
    Certificate certificate = new Certificate();
    certificate.setName(NORMAL_CERTIFICATE_NAME);
    certificate.setDescription(NORMAL_CERTIFICATE_DESCRIPTION);
    certificate.setDuration(NORMAL_CERTIFICATE_DURATION);
    certificate.setPrice(NEGATIVE_CERTIFICATE_PRICE);
    final ValidatorException exception =
        assertThrows(ValidatorException.class, () -> validator.validate(certificate));
    assertTrue(exception.getMessages().contains(NEGATIVE_CERTIFICATE_PRICE_EXCEPTION_MESSAGE));
  }
}

// Generated with love by TestMe :) Please report issues and submit feature requests at:
// http://weirddev.com/forum#!/testme
