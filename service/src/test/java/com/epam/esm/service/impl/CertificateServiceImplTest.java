package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.pagination.PaginationSetting;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class CertificateServiceImplTest {
  @Mock Validator<Certificate> validator;
  @Mock CertificateDAO certificateDAO;
  @Mock Converter<Certificate, CertificateDTO> converter;
  @Mock TagService tagService;
  @Mock Converter<Tag, TagDTO> tagConverter;
  @InjectMocks CertificateServiceImpl certificateServiceImpl;

  private Certificate certificate =
      new Certificate(
          BigInteger.ONE,
          "name",
          "description",
          new BigDecimal(0),
          0,
          LocalDateTime.of(2021, Month.APRIL, 30, 19, 3, 23),
          LocalDateTime.of(2021, Month.APRIL, 30, 19, 3, 23));
  private CertificateDTO certificateDTO =
      new CertificateDTO(
          BigInteger.ONE,
          "name",
          "description",
          new BigDecimal(0),
          0,
          LocalDateTime.of(2021, Month.APRIL, 30, 19, 3, 23),
          LocalDateTime.of(2021, Month.APRIL, 30, 19, 3, 23));

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCount() {
    when(certificateDAO.count()).thenReturn(Long.valueOf(1));

    Long result = certificateServiceImpl.count();
    Assertions.assertEquals(Long.valueOf(1), result);
  }

  @Test
  void testFindAll() {
    when(certificateDAO.findByCriteria(any(), any())).thenReturn(List.of(certificate));

    Collection<CertificateDTO> result =
        certificateServiceImpl.findAll(
            List.of("String"),
            "name",
            "description",
            "sortName",
            "sortDate",
            new PaginationSetting(Integer.valueOf(0), Integer.valueOf(0)),
            "state");
    assertNotNull(result);
    assertEquals(1, result.size());
  }

  @Test
  void testFindById() {
    when(certificateDAO.findById(BigInteger.ONE)).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class, () -> certificateServiceImpl.findById(BigInteger.ONE));

    when(certificateDAO.findById(BigInteger.ONE)).thenReturn(Optional.of(certificate));
    when(converter.convertToDto(certificate)).thenReturn(certificateDTO);
    when(tagConverter.convertToDto(any())).thenReturn(new TagDTO());
    CertificateDTO certificateDTO = certificateServiceImpl.findById(BigInteger.ONE);
    assertNotNull(certificateDTO);
    assertEquals(BigInteger.ONE, certificateDTO.getId());
  }

  @Test
  void testAdd() {
    when(certificateDAO.save(certificate)).thenReturn(certificate);
    when(converter.convertToEntity(certificateDTO)).thenReturn(certificate);
    when(converter.convertToDto(certificate)).thenReturn(certificateDTO);
    doNothing().when(validator).validate(certificate);

    CertificateDTO added = assertDoesNotThrow(()->certificateServiceImpl.add(certificateDTO));
    assertNotNull(added);
  }

  @Test
  void testUpdate() {
    when(certificateDAO.findById(BigInteger.ONE)).thenReturn(Optional.empty());
    when(converter.convertToEntity(certificateDTO)).thenReturn(certificate);
    assertThrows(EntityNotFoundException.class, ()->certificateServiceImpl.update(BigInteger.ONE, certificateDTO, false));
    when(certificateDAO.findById(BigInteger.ONE)).thenReturn(Optional.of(certificate));
    doNothing().when(validator).validate(certificate);
    when(certificateDAO.update(certificate)).thenReturn(certificate);
    assertDoesNotThrow(()->certificateServiceImpl.update(BigInteger.ONE, certificateDTO, true));
  }

  @Test
  void testDelete() {
    when(certificateDAO.findById(BigInteger.ONE)).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class ,()->certificateServiceImpl.delete(BigInteger.ONE));
    when(certificateDAO.findById(BigInteger.TWO)).thenReturn(Optional.of(certificate));
    assertDoesNotThrow(()->certificateServiceImpl.delete(BigInteger.TWO));
  }
}
