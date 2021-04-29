package com.epam.esm.dto.converter.impl;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Converter of order
 *
 * @author Andrey Belik
 * @version 1.0
 */
@Component
public class OrderConverter implements Converter<Order, OrderDTO> {

  /** The Certificate converter. */
  private Converter<Certificate, CertificateDTO> certificateConverter;

  /**
   * Instantiates a new Order converter.
   *
   * @param certificateConverter the certificate converter
   */
  @Autowired
  public OrderConverter(Converter<Certificate, CertificateDTO> certificateConverter) {
    this.certificateConverter = certificateConverter;
  }

  /**
   * Convert to entity order.
   *
   * @param dto the dto
   * @return the order
   */
  @Override
  public Order convertToEntity(OrderDTO dto) {
    final List<Certificate> certificates =
        dto.getCertificates().stream()
            .map(certificateConverter::convertToEntity)
            .collect(Collectors.toList());
    return new Order(dto.getId(), dto.getPrice(), dto.getCreateDate(), certificates);
  }

  /**
   * Convert to dto order dto.
   *
   * @param entity the entity
   * @return the order dto
   */
  @Override
  public OrderDTO convertToDto(Order entity) {
    final List<CertificateDTO> certificateDTOS =
        entity.getCertificates().stream()
            .map(certificateConverter::convertToDto)
            .collect(Collectors.toList());
    return new OrderDTO(entity.getId(), entity.getPrice(), entity.getCreateDate(), certificateDTOS);
  }
}
