package com.epam.esm.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/** The type Order. */
@Entity
@Table(name = "user_order")
public class Order implements Serializable {

  /** The Id. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private BigInteger id;

  /** The Price. */
  @Column(name = "price")
  private BigDecimal price;

  /** The Create date. */
  @Column(name = "create_date")
  private LocalDateTime createDate;

  /** The Certificates. */
  @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.PERSIST})
  @JoinTable(
      name = "order_certificate",
      joinColumns = @JoinColumn(name = "order_id"),
      inverseJoinColumns = @JoinColumn(name = "certificate_id"))
  private List<Certificate> certificates;

  /** The User. */
  @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  /** Instantiates a new Order. */
  public Order() {}

  /**
   * Instantiates a new Order.
   *
   * @param id the id
   * @param price the price
   * @param createDate the create date
   * @param certificates the certificates
   */
  public Order(
      BigInteger id, BigDecimal price, LocalDateTime createDate, List<Certificate> certificates) {
    this.id = id;
    this.price = price;
    this.createDate = createDate;
    this.certificates = certificates;
  }

  /**
   * Instantiates a new Order.
   *
   * @param price the price
   * @param createDate the create date
   * @param certificates the certificates
   * @param user the user
   */
  public Order(
      BigDecimal price, LocalDateTime createDate, List<Certificate> certificates, User user) {
    this.price = price;
    this.createDate = createDate;
    this.certificates = certificates;
    this.user = user;
  }

  /**
   * Sets price.
   *
   * @param price the price
   */
  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  /**
   * Gets certificates.
   *
   * @return the certificates
   */
  public List<Certificate> getCertificates() {
    return certificates;
  }

  /**
   * Sets certificates.
   *
   * @param certificates the certificates
   */
  public void setCertificates(List<Certificate> certificates) {
    this.certificates = certificates;
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
   * Gets price.
   *
   * @return the price
   */
  public BigDecimal getPrice() {
    return price;
  }

  /**
   * Sets cost.
   *
   * @param price the price
   */
  public void setCost(BigDecimal price) {
    this.price = price;
  }

  /**
   * Gets create date.
   *
   * @return the create date
   */
  public LocalDateTime getCreateDate() {
    return createDate;
  }

  /**
   * Sets create date.
   *
   * @param createDate the create date
   */
  public void setCreateDate(LocalDateTime createDate) {
    this.createDate = createDate;
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
    Order order = (Order) o;
    return Objects.equals(id, order.id)
        && Objects.equals(price, order.price)
        && Objects.equals(createDate, order.createDate)
        && Objects.equals(certificates, order.certificates)
        && Objects.equals(user, order.user);
  }

  /**
   * Hash code int.
   *
   * @return the int
   */
  @Override
  public int hashCode() {
    return Objects.hash(id, price, createDate, certificates, user);
  }

  /**
   * To string string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return "Order{"
        + "id="
        + id
        + ", cost="
        + price
        + ", createDate="
        + createDate
        + ", certificate="
        + certificates
        + '}';
  }
}
