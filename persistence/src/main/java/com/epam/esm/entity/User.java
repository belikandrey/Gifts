package com.epam.esm.entity;

import com.epam.esm.audit.Auditable;
import com.epam.esm.audit.AuditableListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/** The type User. */
@Entity
@Table(name = "user")
@EntityListeners(AuditableListener.class)
public class User implements Auditable {
  /** The Id. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private BigInteger id;

  /** The Login. */
  @Column(name = "login")
  private String login;

  /** The Orders. */
  @OneToMany(mappedBy = "user")
  private List<Order> orders;

  @Column(name = "last_update_date")
  private LocalDateTime lastUpdateDate;

  @Column(name = "create_date")
  private LocalDateTime createDate;

  private String operation;

  @Override
  public LocalDateTime getLastUpdateDate() {
    return lastUpdateDate;
  }

  @Override
  public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
    this.lastUpdateDate = lastUpdateDate;
  }

  @Override
  public LocalDateTime getCreateDate() {
    return createDate;
  }

  @Override
  public void setCreateDate(LocalDateTime createDate) {
    this.createDate = createDate;
  }

  @Override
  public String getOperation() {
    return operation;
  }

  @Override
  public void setOperation(String operation) {
    this.operation = operation;
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
   * Gets orders.
   *
   * @return the orders
   */
  public List<Order> getOrders() {
    return orders;
  }

  /**
   * Sets orders.
   *
   * @param orders the orders
   */
  public void setOrders(List<Order> orders) {
    this.orders = orders;
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

  /** Instantiates a new User. */
  public User() {}

  /**
   * Instantiates a new User.
   *
   * @param id the id
   * @param login the login
   */
  public User(BigInteger id, String login) {
    this.id = id;
    this.login = login;
  }

  /**
   * Instantiates a new User.
   *
   * @param id the id
   * @param login the login
   * @param orders the orders
   */
  public User(BigInteger id, String login, List<Order> orders) {
    this.id = id;
    this.login = login;
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
    User user = (User) o;
    return Objects.equals(id, user.id) && Objects.equals(login, user.login);
  }

  /**
   * Hash code int.
   *
   * @return the int
   */
  @Override
  public int hashCode() {
    return Objects.hash(id, login);
  }

  /**
   * To string string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return "User{" + "id=" + id + ", login='" + login + '\'' + '}';
  }
}
