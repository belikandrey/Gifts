package com.epam.esm.dto.converter.impl;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class CertificateConverterTest {
  @Mock Converter<Tag, TagDTO> tagConverter;
  @InjectMocks CertificateConverter certificateConverter;

  private static final TagDTO TAG_DTO = new TagDTO(BigInteger.ONE, "tagName");
  private static final CertificateDTO CERTIFICATE_DTO =
      new CertificateDTO(
          BigInteger.ONE,
          "name",
          "description",
          new BigDecimal(0),
              0,
          LocalDateTime.of(2021, Month.APRIL, 30, 19, 0, 37),
          LocalDateTime.of(2021, Month.APRIL, 30, 19, 0, 37),
              true, Set.of(TAG_DTO));
  private static final Tag TAG = new Tag(BigInteger.ONE, "tagName");
    private static final Certificate CERTIFICATE =
            new Certificate(
                    BigInteger.ONE,
                    "name",
                    "description",
                    new BigDecimal(0),
                    0,
                    LocalDateTime.of(2021, Month.APRIL, 30, 19, 0, 37),
                    LocalDateTime.of(2021, Month.APRIL, 30, 19, 0, 37),
                    true,
                    Set.of(TAG));


  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testConvertToEntity() {
    when(tagConverter.convertToEntity(TAG_DTO)).thenReturn(TAG);
    Certificate result = certificateConverter.convertToEntity(CERTIFICATE_DTO);
    assertNotNull(result);
    assertEquals(1, result.getTags().size());
    assertEquals(CERTIFICATE_DTO.getId(), result.getId());
    assertEquals(CERTIFICATE_DTO.getName(), result.getName());
    assertEquals(CERTIFICATE_DTO.getDescription(), result.getDescription());
    assertEquals(CERTIFICATE_DTO.getPrice(), result.getPrice());
    assertEquals(CERTIFICATE_DTO.getDuration(), result.getDuration());
    assertEquals(CERTIFICATE_DTO.getCreationDate(), result.getCreateDate());
    assertEquals(CERTIFICATE_DTO.getLastUpdateDate(), result.getLastUpdateDate());
  }

  @Test
  void testConvertToDto() {
    when(tagConverter.convertToDto(TAG)).thenReturn(TAG_DTO);
    CertificateDTO result = certificateConverter.convertToDto(CERTIFICATE);
    assertNotNull(result);
    assertEquals(1, result.getTags().size());
    assertEquals(CERTIFICATE.getId(), result.getId());
    assertEquals(CERTIFICATE.getName(), result.getName());
    assertEquals(CERTIFICATE.getDuration(), result.getDuration());
    assertEquals(CERTIFICATE.getDescription(), result.getDescription());
    assertEquals(CERTIFICATE.getPrice(), result.getPrice());
    assertEquals(CERTIFICATE.getCreateDate(), result.getCreationDate());
    assertEquals(CERTIFICATE.getLastUpdateDate(), result.getLastUpdateDate());
  }
}
