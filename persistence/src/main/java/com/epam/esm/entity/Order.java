package com.epam.esm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user_order")
public class Order implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private BigInteger id;

  @Column(name = "price")
  private BigDecimal price;

  @Column(name = "create_date")
  private LocalDateTime createDate;

  @ManyToMany
  @JoinTable(
          name = "order_certificate",
          joinColumns = @JoinColumn(name = "order_id"),
          inverseJoinColumns = @JoinColumn(name = "certificate_id")
  )
  private List<Certificate> certificates;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  public Order() {}

  public Order(BigDecimal price, LocalDateTime createDate, List<Certificate> certificates, User user) {
    this.price = price;
    this.createDate = createDate;
    this.certificates = certificates;
    this.user = user;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public List<Certificate> getCertificates() {
    return certificates;
  }

  public void setCertificates(List<Certificate> certificates) {
    this.certificates = certificates;
  }

  public BigInteger getId() {
    return id;
  }

  public void setId(BigInteger id) {
    this.id = id;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setCost(BigDecimal price) {
    this.price = price;
  }

  public LocalDateTime getCreateDate() {
    return createDate;
  }

  public void setCreateDate(LocalDateTime createDate) {
    this.createDate = createDate;
  }

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

  @Override
  public int hashCode() {
    return Objects.hash(id, price, createDate, certificates, user);
  }

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
