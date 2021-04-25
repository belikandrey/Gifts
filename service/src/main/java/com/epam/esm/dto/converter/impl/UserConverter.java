package com.epam.esm.dto.converter.impl;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter implements Converter<User, UserDTO> {

  private Converter<Order, OrderDTO> orderConverter;

  @Autowired
  public UserConverter(Converter<Order, OrderDTO> orderConverter) {
    this.orderConverter = orderConverter;
  }

  @Override
  public User convertToEntity(UserDTO dto) {
    final List<Order> orders =
        dto.getOrders().stream().map(orderConverter::convertToEntity).collect(Collectors.toList());
    return new User(dto.getId(), dto.getLogin(), orders);
  }

  @Override
  public UserDTO convertToDto(User entity) {
    final List<OrderDTO> orderDTOS =
        entity.getOrders().stream().map(orderConverter::convertToDto).collect(Collectors.toList());
    return new UserDTO(entity.getId(), entity.getLogin(), orderDTOS);
  }
}
