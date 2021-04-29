package com.epam.esm.dao;

import com.epam.esm.dao.pagination.PaginationSetting;
import com.epam.esm.entity.Order;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/** The interface Order dao. */
public interface OrderDAO {
  /**
   * Find all by user id list.
   *
   * @param id the id
   * @param paginationSetting the pagination setting {@link PaginationSetting}
   * @return the list of {@link Order}
   */
  List<Order> findAllByUserId(BigInteger id, PaginationSetting paginationSetting);

  /**
   * Find by id optional.
   *
   * @param id the id
   * @return the {@link Optional} of {@link Order}
   */
  Optional<Order> findById(BigInteger id);

  /**
   * Find all list.
   *
   * @param paginationSetting the pagination setting {@link PaginationSetting}
   * @return the list of {@link Order}
   */
  List<Order> findAll(PaginationSetting paginationSetting);

  /**
   * Save order.
   *
   * @param entity the {@link Order}
   * @return the saved {@link Order}
   */
  Order save(Order entity);
}
