package com.epam.esm.dao;

import com.epam.esm.dao.pagination.PaginationSetting;
import com.epam.esm.entity.Order;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface OrderDAO {
  List<Order> findAllByUserId(BigInteger id, PaginationSetting paginationSetting);

  Optional<Order> findById(BigInteger id);

  List<Order> findAll(PaginationSetting paginationSetting);

  Order save(Order entity);
}
