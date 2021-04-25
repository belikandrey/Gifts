package com.epam.esm.dto;

import org.springframework.hateoas.RepresentationModel;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

public class UserDTO extends RepresentationModel<UserDTO> {

  private BigInteger id;

  private String login;

  private List<OrderDTO> orders;

  public UserDTO(BigInteger id, String login, List<OrderDTO> orders) {
    this.id = id;
    this.login = login;
    this.orders = orders;
  }

  public BigInteger getId() {
    return id;
  }

  public void setId(BigInteger id) {
    this.id = id;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public List<OrderDTO> getOrders() {
    return orders;
  }

  public void setOrders(List<OrderDTO> orders) {
    this.orders = orders;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserDTO userDTO = (UserDTO) o;
    return Objects.equals(id, userDTO.id)
        && Objects.equals(login, userDTO.login)
        && Objects.equals(orders, userDTO.orders);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, login, orders);
  }

  @Override
  public String toString() {
    return "UserDTO{" + "id=" + id + ", login='" + login + '\'' + '}';
  }
}
