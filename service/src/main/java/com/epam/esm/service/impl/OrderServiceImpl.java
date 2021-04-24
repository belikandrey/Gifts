package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

  private OrderDAO orderDAO;

  private UserService userService;

  private Converter<Certificate, CertificateDTO> certificateConverter;

  private CertificateService certificateService;

  @Autowired
  public OrderServiceImpl(
      OrderDAO orderDAO,
      UserService userService,
      Converter<Certificate, CertificateDTO> certificateConverter,
      CertificateService certificateService) {
    this.orderDAO = orderDAO;
    this.userService = userService;
    this.certificateConverter = certificateConverter;
    this.certificateService = certificateService;
  }

  @Override
  @Transactional(readOnly = true)
  public Order findById(BigInteger id) {
    final Optional<Order> orderOptional = orderDAO.findById(id);
    if (orderOptional.isEmpty()) {
      throw new EntityNotFoundException("Order with id : " + id + " not found", Order.class);
    }
    return orderOptional.get();
  }

  @Override
  public Order add(Order order) throws ValidatorException {
    return orderDAO.add(order);
  }

  @Override
  public void delete(BigInteger id) {
    orderDAO.delete(id);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Order> findAll() {
    return orderDAO.findAll();
  }

  @Override
  public Order create(BigInteger userId, List<CertificateDTO> certificateDTOS) {

    certificateDTOS =
        certificateDTOS.stream()
            .map((p) -> certificateService.findById(p.getId()))
            .collect(Collectors.toList());

    User user = userService.findById(userId);
    final List<Certificate> certificates =
        certificateDTOS.stream()
            .map(certificateConverter::convertToEntity)
            .collect(Collectors.toList());

    return orderDAO.create(user, certificates);
  }

  @Override
  @Transactional(readOnly = true)
  public Order findByIdAndUserId(BigInteger orderId, BigInteger userId) {
    final Optional<Order> orderOptional =
        userService.findById(userId).getOrders().stream()
            .filter((p) -> p.getId().equals(orderId))
            .findAny();
    if (orderOptional.isEmpty()) {
      throw new EntityNotFoundException(
          "Order with id : " + orderId + " for user with id : " + userId + " not found",
          Order.class);
    }
    return orderOptional.get();
  }

  @Override
  @Transactional(readOnly = true)
  public List<Order> findAllByUserId(BigInteger id) {
    return orderDAO.findAllByUserId(id);
  }
}
