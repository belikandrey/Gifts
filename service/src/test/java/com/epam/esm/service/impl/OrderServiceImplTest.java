package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.dao.pagination.PaginationSetting;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityNotFoundException;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class OrderServiceImplTest {
  @Mock OrderDAO orderDAO;
  @Mock Converter<Order, OrderDTO> orderConverter;
  @Mock UserDAO userDAO;
  @Mock CertificateDAO certificateDAO;
  @InjectMocks OrderServiceImpl orderServiceImpl;

  private static final Certificate CERTIFICATE =
      new Certificate(
          BigInteger.ONE,
          "name",
          "description",
          new BigDecimal(0),
          0,
          LocalDateTime.of(2021, Month.APRIL, 30, 19, 5, 0),
          LocalDateTime.of(2021, Month.APRIL, 30, 19, 5, 0));
  private static final Order ORDER =
      new Order(
          BigInteger.ONE,
          new BigDecimal(0),
          LocalDateTime.of(2021, Month.APRIL, 30, 19, 5, 0),
          List.of(CERTIFICATE));

  private static final CertificateDTO CERTIFICATE_DTO =
      new CertificateDTO(
          BigInteger.ONE,
          "name",
          "description",
          new BigDecimal(0),
          0,
          LocalDateTime.of(2021, Month.APRIL, 30, 19, 5, 0),
          LocalDateTime.of(2021, Month.APRIL, 30, 19, 5, 0));
  private static final OrderDTO ORDER_DTO =
      new OrderDTO(
          BigInteger.ONE,
          new BigDecimal(0),
          LocalDateTime.of(2021, Month.APRIL, 30, 19, 5, 0),
          List.of(CERTIFICATE_DTO));

  private static final PaginationSetting PAGINATION_SETTING = PaginationSetting.getInstance(0, 0);
  private static final User USER = new User(BigInteger.ONE, "login", List.of(ORDER));

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testFindById() {
    when(orderDAO.findById(BigInteger.ONE)).thenReturn(Optional.of(ORDER));
    when(orderConverter.convertToDto(ORDER)).thenReturn(ORDER_DTO);

    OrderDTO result = orderServiceImpl.findById(BigInteger.ONE);
    assertNotNull(result);
    assertDoesNotThrow(() -> result.getCertificates().get(0));
    assertEquals(ORDER.getId(), result.getId());
    assertEquals(ORDER.getPrice(), result.getPrice());
    assertEquals(ORDER.getCreateDate(), result.getCreateDate());
  }

  @Test
  void testFindAll() {
    when(orderDAO.findAll(PAGINATION_SETTING)).thenReturn(List.of(ORDER));

    List<Order> result = orderServiceImpl.findAll(PAGINATION_SETTING);
    assertNotNull(result);
    assertTrue(result.size() > 0);
    assertEquals(ORDER.getId(), result.get(0).getId());
    assertEquals(ORDER.getPrice(), result.get(0).getPrice());
    assertEquals(ORDER.getCreateDate(), result.get(0).getCreateDate());
  }

  @Test
  void testCreate() {
    when(orderDAO.save(ORDER)).thenReturn(ORDER);
    when(orderConverter.convertToDto(any())).thenReturn(ORDER_DTO);
    when(userDAO.findById(BigInteger.ONE)).thenReturn(Optional.of(USER));
    when(certificateDAO.findById(any())).thenReturn(Optional.of(CERTIFICATE));

    OrderDTO result = orderServiceImpl.create(BigInteger.ONE, new ArrayList<>());
    assertNotNull(result);
    assertEquals(ORDER.getId(), result.getId());
    assertEquals(ORDER.getPrice(), result.getPrice());
  }

  @Test
  void testFindByIdAndUserId() {
    when(userDAO.findById(any())).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class, ()->orderServiceImpl.findByIdAndUserId(null, null));
    when(userDAO.findById(any())).thenReturn(Optional.of(USER));
    when(orderConverter.convertToDto(ORDER)).thenReturn(ORDER_DTO);
    OrderDTO order = orderServiceImpl.findByIdAndUserId(ORDER.getId(), USER.getId());
    assertNotNull(order);
    assertThrows(EntityNotFoundException.class, ()->orderServiceImpl.findByIdAndUserId(BigInteger.ZERO, BigInteger.ZERO));
  }

  @Test
  void testFindAllByUserId() {
    when(orderDAO.findAllByUserId(any(), any())).thenReturn(List.of(ORDER));

    List<OrderDTO> result =
        orderServiceImpl.findAllByUserId(
            null, PAGINATION_SETTING);
    assertNotNull(result);
    assertEquals(1, result.size());
    assertDoesNotThrow(()->result.get(0));
  }
}
