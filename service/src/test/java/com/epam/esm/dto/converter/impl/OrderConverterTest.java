package com.epam.esm.dto.converter.impl;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class OrderConverterTest {
    @Mock
    Converter<Certificate, CertificateDTO> certificateConverter;
    @InjectMocks
    OrderConverter orderConverter;

    private final static CertificateDTO CERTIFICATE_DTO = new CertificateDTO(BigInteger.ONE, "name", "description", new BigDecimal(0), Integer.valueOf(0), LocalDateTime.of(2021, Month.APRIL, 30, 19, 1, 3), LocalDateTime.of(2021, Month.APRIL, 30, 19, 1, 3), true);
    private final static OrderDTO ORDER_DTO = new OrderDTO(BigInteger.TWO, new BigDecimal(0), LocalDateTime.of(2021, Month.APRIL, 30, 19, 1, 3), List.of(CERTIFICATE_DTO));
    private final static Certificate CERTIFICATE = new Certificate(BigInteger.ONE, "name", "description", new BigDecimal(0), Integer.valueOf(0), LocalDateTime.of(2021, Month.APRIL, 30, 19, 1, 3), LocalDateTime.of(2021, Month.APRIL, 30, 19, 1, 3));
    private final static Order ORDER = new Order(BigInteger.TWO, new BigDecimal(0), LocalDateTime.of(2021, Month.APRIL, 30, 19, 1, 3), List.of(CERTIFICATE));

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConvertToEntity(){
        when(certificateConverter.convertToEntity(CERTIFICATE_DTO)).thenReturn(CERTIFICATE);
        Order result = orderConverter.convertToEntity(ORDER_DTO);
        assertNotNull(result);
        assertDoesNotThrow(()->result.getCertificates().get(0));
        assertEquals(ORDER_DTO.getId(), result.getId());
        assertEquals(ORDER_DTO.getPrice(), result.getPrice());
        assertEquals(ORDER_DTO.getCreateDate(), result.getCreateDate());
    }

    @Test
    void testConvertToDto(){
        when(certificateConverter.convertToDto(CERTIFICATE)).thenReturn(CERTIFICATE_DTO);
        OrderDTO result = orderConverter.convertToDto(ORDER);
        assertNotNull(result);
        assertDoesNotThrow(()->result.getCertificates().get(0));
        assertEquals(ORDER.getId(), result.getId());
        assertEquals(ORDER.getPrice(), result.getPrice());
        assertEquals(ORDER.getCreateDate(), result.getCreateDate());
    }
}
