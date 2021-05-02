package com.epam.esm.dao.impl;

import com.epam.esm.config.DaoTestConfig;
import com.epam.esm.dao.pagination.PaginationSetting;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = DaoTestConfig.class)
@Transactional
class OrderDAOImplTest {
  private OrderDAOImpl orderDAO;

  private static final BigInteger USER_ID = BigInteger.ONE;

  @Autowired
  public OrderDAOImplTest(OrderDAOImpl orderDAO) {
    this.orderDAO = orderDAO;
  }

  @Test
  public void findAllByUserIdTest() {
    final List<Order> orders = orderDAO.findAllByUserId(USER_ID, new PaginationSetting(10, 1));
    assertNotNull(orders);
    assertFalse(orders.isEmpty());
    assertEquals(2, orders.size());
  }

}
