package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.dao.pagination.PaginationSetting;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class UserServiceImplTest {
    @Mock
    UserDAO userDAO;
    @Mock
    Converter<User, UserDTO> converter;
    @InjectMocks
    UserServiceImpl userServiceImpl;

    private static final User USER = new User(BigInteger.ONE, "login");
    private static final UserDTO USER_DTO = new UserDTO(BigInteger.ONE, "login");
    private static final PaginationSetting PAGINATION_SETTING = PaginationSetting.getInstance(0, 0);
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testFindById(){
        when(userDAO.findById(BigInteger.ONE)).thenReturn(Optional.of(USER));
        when(converter.convertToDto(USER)).thenReturn(USER_DTO);

        UserDTO result = userServiceImpl.findById(BigInteger.ONE);
        assertNotNull(result);
        assertEquals(BigInteger.ONE, result.getId());
    }

    @Test
    void testFindAll(){
        when(userDAO.findAll(PAGINATION_SETTING)).thenReturn(List.of(USER));
        when(converter.convertToDto(USER)).thenReturn(USER_DTO);

        List<UserDTO> result = userServiceImpl.findAll(PAGINATION_SETTING);
        assertNotNull(result);
        assertDoesNotThrow(()->result.get(0));
        assertTrue(result.contains(USER_DTO));
    }
}