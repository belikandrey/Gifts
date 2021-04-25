package com.epam.esm.service;

import com.epam.esm.dao.pagination.Pageable;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.entity.Order;

import java.math.BigInteger;
import java.util.List;

public interface OrderService extends EntityService<OrderDTO, BigInteger> {
    List<Order> findAll();

    OrderDTO create(BigInteger userId, List<CertificateDTO> certificates);

    OrderDTO findByIdAndUserId(BigInteger orderId, BigInteger userId);

    List<OrderDTO> findAllByUserId(BigInteger id, Pageable pageable);
}
