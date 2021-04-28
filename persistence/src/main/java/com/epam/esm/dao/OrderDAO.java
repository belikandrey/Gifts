package com.epam.esm.dao;

import com.epam.esm.dao.pagination.Pageable;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface OrderDAO {
  List<Order> findAllByUserId(BigInteger id, Pageable pageable);

  List<Order> findAllByUserId(BigInteger id);

  Optional<Order> findById(BigInteger id);

  List<Order> findAll(Pageable pageable);

  Order save(Order entity);

  Order update(Order entity);

  void deleteById(BigInteger id);
}
