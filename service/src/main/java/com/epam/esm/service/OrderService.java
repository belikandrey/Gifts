package com.epam.esm.service;

import com.epam.esm.dao.pagination.PaginationSetting;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.entity.Order;

import java.math.BigInteger;
import java.util.List;

/** The interface Order service. */
public interface OrderService extends EntityService<OrderDTO, BigInteger> {

  /**
   * Find all list.
   *
   * @param paginationSetting the pagination setting {@link PaginationSetting}
   * @return the list of {@link Order}
   */
  List<Order> findAll(PaginationSetting paginationSetting);

  /**
   * Create order dto.
   *
   * @param userId the user id
   * @param certificates the certificates
   * @return the {@link OrderDTO}
   */
  OrderDTO create(BigInteger userId, List<CertificateDTO> certificates);

  /**
   * Find by id and user id order dto.
   *
   * @param orderId the order id
   * @param userId the user id
   * @return the {@link OrderDTO}
   */
  OrderDTO findByIdAndUserId(BigInteger orderId, BigInteger userId);

  /**
   * Find all by user id list.
   *
   * @param id the id
   * @param paginationSetting the pagination setting {@link PaginationSetting}
   * @return the list of {@link OrderDTO}
   */
  List<OrderDTO> findAllByUserId(BigInteger id, PaginationSetting paginationSetting);

  OrderDTO create(OrderDTO order);
}
