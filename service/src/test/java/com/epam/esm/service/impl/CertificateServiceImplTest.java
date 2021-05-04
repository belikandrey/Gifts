package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.pagination.PaginationSetting;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.converter.impl.CertificateConverter;
import com.epam.esm.dto.converter.impl.TagConverter;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.impl.CertificateValidator;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class CertificateServiceImplTest {
  @Mock CertificateValidator validator;
  @Mock CertificateDAO certificateDAO;
  @Mock CertificateConverter converter;
  @Mock TagService tagService;
  @Mock TagConverter tagConverter;
  @InjectMocks CertificateServiceImpl certificateServiceImpl;

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
    when(certificateDAO.findByCriteria(any(), any())).thenReturn(List.of(getCertificate()));

    Collection<CertificateDTO> result =
        certificateServiceImpl.findAll(
            List.of("String"),
            "name",
            "description",
            "sortName",
            "sortDate",
            PaginationSetting.getInstance(0, 0),
            "state");
    assertNotNull(result);
    assertEquals(1, result.size());
  }

  @Test
  void testFindById() {
    when(certificateDAO.findById(any(BigInteger.class))).thenReturn(Optional.empty());
    assertThrows(
        EntityNotFoundException.class, () -> certificateServiceImpl.findById(BigInteger.ONE));

    when(certificateDAO.findById(any(BigInteger.class))).thenReturn(Optional.of(getCertificate()));
    when(converter.convertToDto(any(Certificate.class))).thenReturn(getCertificateDTO());
    when(tagConverter.convertToDto(any(Tag.class))).thenReturn(new TagDTO(BigInteger.ONE, "#cool"));
    CertificateDTO certificateDTO = certificateServiceImpl.findById(BigInteger.ONE);
    assertNotNull(certificateDTO);
    assertEquals(BigInteger.ONE, certificateDTO.getId());
  }

  @Test
  void testAdd() {
    when(converter.convertToEntity(any(CertificateDTO.class))).thenReturn(getCertificate());
    when(certificateDAO.save(any(Certificate.class))).thenReturn(getCertificate());
    when(converter.convertToDto(any(Certificate.class))).thenReturn(getCertificateDTO());
    doNothing().when(validator).validate(any(Certificate.class));
    when(tagConverter.convertToEntity(any(TagDTO.class))).thenReturn(new Tag());
    CertificateDTO added =
        assertDoesNotThrow(() -> certificateServiceImpl.add(getCertificateDTO()));
    assertNotNull(added);
  }

  @Test
  void testUpdate() {
    when(certificateDAO.findById(any(BigInteger.class))).thenReturn(Optional.empty());
    when(converter.convertToEntity(any(CertificateDTO.class))).thenReturn(getCertificate());
    assertThrows(
        EntityNotFoundException.class,
        () -> certificateServiceImpl.update(BigInteger.ONE, getCertificateDTO(), false));
    when(certificateDAO.findById(any(BigInteger.class))).thenReturn(Optional.of(getCertificate()));
    doNothing().when(validator).validate(any(Certificate.class));
    when(certificateDAO.update(any(Certificate.class))).thenReturn(getCertificate());
    assertDoesNotThrow(
        () -> certificateServiceImpl.update(BigInteger.ONE, getCertificateDTO(), true));
  }

  @Test
  void testDelete() {
    when(certificateDAO.findById(any(BigInteger.class))).thenReturn(Optional.empty());
    assertThrows(
        EntityNotFoundException.class, () -> certificateServiceImpl.delete(BigInteger.ONE));
    when(certificateDAO.findById(any(BigInteger.class))).thenReturn(Optional.of(getCertificate()));
    assertDoesNotThrow(() -> certificateServiceImpl.delete(BigInteger.TWO));
  }

  private Certificate getCertificate() {
    return new Certificate(
        BigInteger.ONE,
        "name",
        "description",
        new BigDecimal(0),
        0,
        LocalDateTime.of(2021, Month.APRIL, 30, 19, 3, 23),
        LocalDateTime.of(2021, Month.APRIL, 30, 19, 3, 23));
  }

  private CertificateDTO getCertificateDTO() {
    return new CertificateDTO(
        BigInteger.ONE,
        "name",
        "description",
        new BigDecimal(0),
        0,
        LocalDateTime.of(2021, Month.APRIL, 30, 19, 3, 23),
        LocalDateTime.of(2021, Month.APRIL, 30, 19, 3, 23));
  }
}
