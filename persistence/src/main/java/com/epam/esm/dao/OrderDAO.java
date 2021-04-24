package com.epam.esm.dao;

import com.epam.esm.dao.pagination.Pageable;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;

import java.math.BigInteger;
import java.util.List;

public interface OrderDAO extends AbstractDAO<Order, BigInteger>{
    List<Order> findAll();

    List<Order> findAllByUserId(BigInteger id, Pageable pageable);

    Order create(User user, List<Certificate> certificates);
}
