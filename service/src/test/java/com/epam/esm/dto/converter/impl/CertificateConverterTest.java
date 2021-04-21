package com.epam.esm.dto.converter.impl;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.entity.Certificate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CertificateConverterTest {
/*
  private static CertificateConverter converter;
  private static final BigInteger ID = BigInteger.ONE;
  private static final String NAME = "Apple.com";
  private static final String DESCRIPTION = "Certificate for Iphone X";
  private static final BigDecimal PRICE = BigDecimal.TEN;
  private static final int DURATION = 42;
  private static final LocalDateTime CREATE_DATE = LocalDateTime.now();
  private static final LocalDateTime LAST_UPDATE_DATE = LocalDateTime.now();

  @BeforeAll
  public static void init() {
    converter = new CertificateConverter();
  }

  @Test
  public void convertToDtoTest() {
    Certificate certificate =
        new Certificate(ID, NAME, DESCRIPTION, PRICE, DURATION, CREATE_DATE, LAST_UPDATE_DATE);
    final CertificateDTO certificateDTO = converter.convert(certificate);
    assertNotNull(certificateDTO);
    assertEquals(certificate.getId(), certificateDTO.getId());
    assertEquals(certificate.getName(), certificateDTO.getName());
    assertEquals(certificate.getDescription(), certificateDTO.getDescription());
    assertEquals(certificate.getPrice(), certificateDTO.getPrice());
    assertEquals(certificate.getDuration(), certificateDTO.getDuration());
    assertEquals(certificate.getCreateDate(), certificateDTO.getCreationDate());
    assertEquals(certificate.getLastUpdateDate(), certificateDTO.getLastUpdateDate());
  }

  @Test
  public void convertToEntityTest() {
    CertificateDTO certificateDTO =
        new CertificateDTO(ID, NAME, DESCRIPTION, PRICE, DURATION, CREATE_DATE, LAST_UPDATE_DATE);
    final Certificate certificate = converter.convert(certificateDTO);
    assertNotNull(certificateDTO);
    assertEquals(certificate.getId(), certificateDTO.getId());
    assertEquals(certificate.getName(), certificateDTO.getName());
    assertEquals(certificate.getDescription(), certificateDTO.getDescription());
    assertEquals(certificate.getPrice(), certificateDTO.getPrice());
    assertEquals(certificate.getDuration(), certificateDTO.getDuration());
    assertEquals(certificate.getCreateDate(), certificateDTO.getCreationDate());
    assertEquals(certificate.getLastUpdateDate(), certificateDTO.getLastUpdateDate());
  }*/
}
