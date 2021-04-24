package com.epam.esm.service;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.entity.Order;

import java.math.BigInteger;
import java.util.List;

public interface OrderService extends EntityService<Order, BigInteger> {
    List<Order> findAll();

    Order create(BigInteger userId, List<CertificateDTO> certificates);

    Order findByIdAndUserId(BigInteger orderId, BigInteger userId);

    List<Order> findAllByUserId(BigInteger id);
}
