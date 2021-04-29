package com.epam.esm.service;

import com.epam.esm.dao.pagination.PaginationSetting;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.entity.Order;

import java.math.BigInteger;
import java.util.List;

public interface OrderService extends EntityService<OrderDTO, BigInteger> {

  List<Order> findAll(PaginationSetting paginationSetting);

  OrderDTO create(BigInteger userId, List<CertificateDTO> certificates);

  OrderDTO findByIdAndUserId(BigInteger orderId, BigInteger userId);

  List<OrderDTO> findAllByUserId(BigInteger id, PaginationSetting paginationSetting);
}
