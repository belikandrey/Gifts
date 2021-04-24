package com.epam.esm.service;

import com.epam.esm.entity.Order;

import java.math.BigInteger;
import java.util.List;

public interface OrderService extends EntityService<Order, BigInteger> {
    List<Order> findAll();
}
