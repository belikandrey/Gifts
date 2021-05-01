package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

/** The type User dto. */
@Relation(collectionRelation = "users")
public class UserDTO extends RepresentationModel<UserDTO> {

  /** The Id. */
  private BigInteger id;

  /** The Login. */
  private String login;

  /** The Orders. */
  @JsonIgnore private List<OrderDTO> orders;

  /**
   * Instantiates a new User dto.
   *
   * @param id the id
   * @param login the login
   */
  public UserDTO(BigInteger id, String login) {
    this.id = id;
    this.login = login;
  }

  /**
   * Instantiates a new User dto.
   *
   * @param id the id
   * @param login the login
   * @param orders the orders
   */
  public UserDTO(BigInteger id, String login, List<OrderDTO> orders) {
    this.id = id;
    this.login = login;
    this.orders = orders;
  }

  /**
   * Gets id.
   *
   * @return the id
   */
  public BigInteger getId() {
    return id;
  }

  /**
   * Sets id.
   *
   * @param id the id
   */
  public void setId(BigInteger id) {
    this.id = id;
  }

  /**
   * Gets login.
   *
   * @return the login
   */
  public String getLogin() {
    return login;
  }

  /**
   * Sets login.
   *
   * @param login the login
   */
  public void setLogin(String login) {
    this.login = login;
  }

  /**
   * Gets orders.
   *
   * @return the orders
   */
  public List<OrderDTO> getOrders() {
    return orders;
  }

  /**
   * Sets orders.
   *
   * @param orders the orders
   */
  public void setOrders(List<OrderDTO> orders) {
    this.orders = orders;
  }

  /**
   * Equals boolean.
   *
   * @param o the o
   * @return the boolean
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    UserDTO userDTO = (UserDTO) o;
    return Objects.equals(id, userDTO.id) && Objects.equals(login, userDTO.login);
  }

  /**
   * Hash code int.
   *
   * @return the int
   */
  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), id, login);
  }

  /**
   * To string string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return "UserDTO{" + "id=" + id + ", login='" + login + '\'' + '}';
  }
}
