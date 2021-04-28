package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.pagination.Pageable;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.UserDTO;
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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

  private OrderDAO orderDAO;

  private UserService userService;

  private Converter<Certificate, CertificateDTO> certificateConverter;

  private Converter<Order, OrderDTO> orderConverter;

  private CertificateService certificateService;

  private Converter<User, UserDTO> userConverter;

  @Autowired
  public OrderServiceImpl(
      OrderDAO orderDAO,
      UserService userService,
      Converter<Certificate, CertificateDTO> certificateConverter,
      CertificateService certificateService,
      Converter<Order, OrderDTO> orderConverter,
      Converter<User, UserDTO> userConverter) {
    this.orderDAO = orderDAO;
    this.userService = userService;
    this.certificateConverter = certificateConverter;
    this.certificateService = certificateService;
    this.orderConverter = orderConverter;
    this.userConverter = userConverter;
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

  // TODO fix
  @Override
  public OrderDTO create(BigInteger userId, List<CertificateDTO> certificateDTOS) {

    certificateDTOS =
        certificateDTOS.stream()
            .map((p) -> certificateService.findById(p.getId()))
            .collect(Collectors.toList());

    User user = userConverter.convertToEntity(userService.findById(userId));
    final List<Certificate> certificates =
        certificateDTOS.stream()
            .map(certificateConverter::convertToEntity)
            .collect(Collectors.toList());
    double sum = certificates.stream().mapToDouble((p) -> p.getPrice().doubleValue()).sum();
    final Order order = new Order(BigDecimal.valueOf(sum), LocalDateTime.now(), certificates, user);
    user.getOrders().add(order);
    // return orderConverter.convertToDto(orderDAO.save(user, certificates));
    // return orderConverter.convertToDto(orderDAO.save(order));
    userService.update(userConverter.convertToDto(user));
    return orderConverter.convertToDto(order);
  }

  @Override
  @Transactional(readOnly = true)
  public OrderDTO findByIdAndUserId(BigInteger orderId, BigInteger userId) {
    final Optional<OrderDTO> orderOptional =
        userConverter.convertToEntity(userService.findById(userId)).getOrders().stream()
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
