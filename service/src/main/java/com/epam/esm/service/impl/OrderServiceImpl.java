package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.dao.pagination.Pageable;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityDisabledException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

  private OrderDAO orderDAO;

  private Converter<Order, OrderDTO> orderConverter;

  private UserDAO userDAO;

  private CertificateDAO certificateDAO;

  @Autowired
  public OrderServiceImpl(
      OrderDAO orderDAO,
      Converter<Order, OrderDTO> orderConverter,
      UserDAO userDAO,
      CertificateDAO certificateDAO) {
    this.orderDAO = orderDAO;
    this.orderConverter = orderConverter;
    this.userDAO = userDAO;
    this.certificateDAO = certificateDAO;
  }

  @Override
  @Transactional(readOnly = true)
  public OrderDTO findById(BigInteger id) {
    final Optional<Order> orderOptional = orderDAO.findById(id);
    if (orderOptional.isEmpty()) {
      throw new EntityNotFoundException("Order with id : " + id + " not found", Order.class);
    }
    return orderConverter.convertToDto(orderOptional.get());
  }

  @Override
  public OrderDTO add(OrderDTO orderDTO) throws ValidatorException {
    final Order order = orderConverter.convertToEntity(orderDTO);
    return orderConverter.convertToDto(orderDAO.save(order));
  }

  @Override
  public void delete(BigInteger id) {
    orderDAO.deleteById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Order> findAll(Pageable pageable) {
    return orderDAO.findAll(pageable);
  }

  @Override
  public OrderDTO create(BigInteger userId, List<CertificateDTO> certificateDTOS) {
    List<Certificate> certificates = getCertificatesForCreateOrder(certificateDTOS);
    final double sum = certificates.stream().mapToDouble((p) -> p.getPrice().doubleValue()).sum();
    final Optional<User> user = userDAO.findById(userId);
    if (user.isEmpty()) {
      throw new EntityNotFoundException("User with id : " + userId + " not found", User.class);
    }
    Order order = new Order(BigDecimal.valueOf(sum), LocalDateTime.now(), certificates, user.get());
    order = orderDAO.save(order);
    return orderConverter.convertToDto(order);
  }

  private List<Certificate> getCertificatesForCreateOrder(List<CertificateDTO> certificateDTOS) {
    List<Certificate> certificates = new ArrayList<>();
    for (CertificateDTO certificateDTO : certificateDTOS) {
      Optional<Certificate> certificateOptional = certificateDAO.findById(certificateDTO.getId());
      if (certificateOptional.isEmpty()) {
        throw new EntityNotFoundException(
            "Certificate with id : " + certificateDTO.getId() + " not found", Certificate.class);
      }
      final Certificate certificate = certificateOptional.get();
      if (!certificate.getEnabled()) {
        throw new EntityDisabledException(
            "Certificate with id : " + certificate.getId() + " is disabled", Certificate.class);
      }
      certificates.add(certificate);
    }
    return certificates;
  }

  @Override
  @Transactional(readOnly = true)
  public OrderDTO findByIdAndUserId(BigInteger orderId, BigInteger userId) {
    final Optional<User> user = userDAO.findById(userId);
    if (user.isEmpty()) {
      throw new EntityNotFoundException("User with id : " + userId + " not found", User.class);
    }
    final Optional<OrderDTO> orderOptional =
        user.get().getOrders().stream()
            .filter((p) -> p.getId().equals(orderId))
            .map(orderConverter::convertToDto)
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
  public List<OrderDTO> findAllByUserId(BigInteger id, Pageable pageable) {
    return orderDAO.findAllByUserId(id, pageable).stream()
        .map(orderConverter::convertToDto)
        .collect(Collectors.toList());
  }

  @Override
  public List<OrderDTO> findAllByUserId(BigInteger id) {
    return orderDAO.findAllByUserId(id).stream()
        .map(orderConverter::convertToDto)
        .collect(Collectors.toList());
  }
}
