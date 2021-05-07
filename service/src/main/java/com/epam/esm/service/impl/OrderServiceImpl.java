package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.dao.pagination.PaginationSetting;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityDisabledException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.messages.Translator;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/** The type Order service. */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

  /** The Order dao. */
  private OrderDAO orderDAO;

  /** The Order converter. */
  private Converter<Order, OrderDTO> orderConverter;

  /** The User dao. */
  private UserDAO userDAO;

  /** The Certificate dao. */
  private CertificateDAO certificateDAO;

  private Translator translator;

  private static final String ORDER_WITH_ID_KEY = "order.order_with_id";
  private static final String USER_WITH_ID_KEY = "user.user_with_id";
  private static final String NOT_FOUND_KEY = "service.not_found";
  private static final String ORDER_FOR_USER_KEY = "order.for_user_with_id";
  private static final String CERTIFICATE_WITH_ID_KEY = "certificate.certificate_with_id";
  private static final String CERTIFICATE_IS_DISABLED_KEY = "certificate.disabled";
  /**
   * Instantiates a new Order service.
   *
   * @param orderDAO the {@link OrderDAO}
   * @param orderConverter the {@link Converter}
   * @param userDAO the {@link UserDAO}
   * @param certificateDAO the {@link CertificateDAO}
   * @param translator
   */
  @Autowired
  public OrderServiceImpl(
      OrderDAO orderDAO,
      Converter<Order, OrderDTO> orderConverter,
      UserDAO userDAO,
      CertificateDAO certificateDAO,
      Translator translator) {
    this.orderDAO = orderDAO;
    this.orderConverter = orderConverter;
    this.userDAO = userDAO;
    this.certificateDAO = certificateDAO;
    this.translator = translator;
  }

  /**
   * Find by id order dto.
   *
   * @param id the id
   * @return the {@link OrderDTO}
   */
  @Override
  @Transactional(readOnly = true)
  public OrderDTO findById(BigInteger id) {
    final Order order =
        orderDAO
            .findById(id)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        translator.toLocale(ORDER_WITH_ID_KEY)
                            + " : "
                            + id
                            + " "
                            + translator.toLocale(NOT_FOUND_KEY),
                        Order.class));

    return orderConverter.convertToDto(order);
  }

  /**
   * Find all list.
   *
   * @param paginationSetting the pagination setting
   * @return the list of
   */
  @Override
  @Transactional(readOnly = true)
  public List<Order> findAll(PaginationSetting paginationSetting) {
    return orderDAO.findAll(paginationSetting);
  }

  /**
   * Create order dto.
   *
   * @param userId the user id
   * @param certificateDTOS the certificate dtos
   * @return the order dto
   */
  @Override
  public OrderDTO create(BigInteger userId, List<CertificateDTO> certificateDTOS) {
    List<Certificate> certificates = getCertificatesForCreateOrder(certificateDTOS);
    final double sum = certificates.stream().mapToDouble((p) -> p.getPrice().doubleValue()).sum();
    final User user =
        userDAO
            .findById(userId)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        translator.toLocale(ORDER_WITH_ID_KEY)
                            + " : "
                            + userId
                            + " "
                            + translator.toLocale(NOT_FOUND_KEY),
                        User.class));
    Order order = new Order(BigDecimal.valueOf(sum), LocalDateTime.now(), certificates, user);
    order = orderDAO.save(order);
    return orderConverter.convertToDto(order);
  }

  /**
   * Gets certificates for create order.
   *
   * @param certificateDTOS the certificate dtos
   * @return the certificates for create order
   */
  private List<Certificate> getCertificatesForCreateOrder(List<CertificateDTO> certificateDTOS) {
    List<Certificate> certificates = new ArrayList<>();
    for (CertificateDTO certificateDTO : certificateDTOS) {
      Certificate certificate =
          certificateDAO
              .findById(certificateDTO.getId())
              .orElseThrow(
                  () ->
                      new EntityNotFoundException(
                          translator.toLocale(CERTIFICATE_WITH_ID_KEY)
                              + " : "
                              + certificateDTO.getId()
                              + " "
                              + translator.toLocale(NOT_FOUND_KEY),
                          Certificate.class));
      if (!certificate.getEnabled()) {
        throw new EntityDisabledException(
            translator.toLocale(CERTIFICATE_WITH_ID_KEY)
                + " : "
                + certificate.getId()
                + " "
                + translator.toLocale(CERTIFICATE_IS_DISABLED_KEY),
            Certificate.class);
      }
      certificates.add(certificate);
    }
    return certificates;
  }

  /**
   * Find by id and user id order dto.
   *
   * @param orderId the order id
   * @param userId the user id
   * @return the order dto
   */
  @Override
  @Transactional(readOnly = true)
  public OrderDTO findByIdAndUserId(BigInteger orderId, BigInteger userId) {
    User user =
        userDAO
            .findById(userId)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        translator.toLocale(USER_WITH_ID_KEY)
                            + " : "
                            + userId
                            + " "
                            + translator.toLocale(NOT_FOUND_KEY),
                        User.class));
    return user.getOrders().stream()
        .filter((p) -> p.getId().equals(orderId))
        .map(orderConverter::convertToDto)
        .findAny()
        .orElseThrow(
            () ->
                new EntityNotFoundException(
                    translator.toLocale(ORDER_WITH_ID_KEY)
                        + " : "
                        + orderId
                        + " "
                        + translator.toLocale(ORDER_FOR_USER_KEY)
                        + " : "
                        + userId
                        + " "
                        + translator.toLocale(NOT_FOUND_KEY),
                    Order.class));
  }

  /**
   * Find all by user id list.
   *
   * @param id the id
   * @param paginationSetting the pagination setting
   * @return the list
   */
  @Override
  @Transactional(readOnly = true)
  public List<OrderDTO> findAllByUserId(BigInteger id, PaginationSetting paginationSetting) {
    return orderDAO.findAllByUserId(id, paginationSetting).stream()
        .map(orderConverter::convertToDto)
        .collect(Collectors.toList());
  }
}
