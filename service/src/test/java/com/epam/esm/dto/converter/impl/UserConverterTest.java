package com.epam.esm.dto.converter.impl;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
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
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
class UserConverterTest {
    @Mock
    Converter<Order, OrderDTO> orderConverter;
    @InjectMocks
    UserConverter userConverter;

    private static final Certificate CERTIFICATE = new Certificate(BigInteger.ONE, "name", "description", new BigDecimal(0), Integer.valueOf(0), LocalDateTime.of(2021, Month.APRIL, 30, 19, 1, 20), LocalDateTime.of(2021, Month.APRIL, 30, 19, 1, 20));
    private static final Order ORDER = new Order(BigInteger.ONE, new BigDecimal(0), LocalDateTime.of(2021, Month.APRIL, 30, 19, 1, 20), Arrays.<Certificate>asList(CERTIFICATE));
    private static final User USER = new User(BigInteger.ONE, "login", Arrays.<Order>asList(ORDER));
    private static final CertificateDTO CERTIFICATE_DTO = new CertificateDTO(BigInteger.ONE, "name", "description", new BigDecimal(0), Integer.valueOf(0), LocalDateTime.of(2021, Month.APRIL, 30, 19, 1, 20), LocalDateTime.of(2021, Month.APRIL, 30, 19, 1, 20));
    private static final OrderDTO ORDER_DTO = new OrderDTO(BigInteger.ONE, new BigDecimal(0), LocalDateTime.of(2021, Month.APRIL, 30, 19, 1, 20), List.of(CERTIFICATE_DTO));
    private static final UserDTO USER_DTO = new UserDTO(BigInteger.ONE, "login", List.of(ORDER_DTO));

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConvertToEntity(){
        when(orderConverter.convertToEntity(ORDER_DTO)).thenReturn(ORDER);
        User result = userConverter.convertToEntity(USER_DTO);
        assertNotNull(result);
        assertEquals(result.getId(), USER_DTO.getId());
        assertEquals(result.getLogin(), USER_DTO.getLogin());
    }

    @Test
    void testConvertToDto(){
        when(orderConverter.convertToDto(ORDER)).thenReturn(ORDER_DTO);
        UserDTO result = userConverter.convertToDto(USER);
        assertNotNull(result);
        assertDoesNotThrow(()->result.getOrders().get(0));
        assertEquals(result.getId(), USER.getId());
        assertEquals(result.getLogin(), USER.getLogin());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme