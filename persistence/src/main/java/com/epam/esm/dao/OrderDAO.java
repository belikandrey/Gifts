package com.epam.esm.dao;

import com.epam.esm.entity.Order;

import java.math.BigInteger;
import java.util.List;

public interface OrderDAO extends AbstractDAO<Order, BigInteger>{
    List<Order> findAll();
}
