package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/** The type Order dto. */
@Relation(collectionRelation = "orders")
public class OrderDTO extends RepresentationModel<OrderDTO> {

  /** The Id. */
  private BigInteger id;

  /** The Price. */
  private BigDecimal price;

  /** The Create date. */
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private LocalDateTime createDate;

  /** The Certificates. */
  private List<CertificateDTO> certificates;

  /**
   * Instantiates a new Order dto.
   *
   * @param id the id
   * @param price the price
   * @param createDate the create date
   * @param certificates the certificates
   */
  public OrderDTO(
      BigInteger id,
      BigDecimal price,
      LocalDateTime createDate,
      List<CertificateDTO> certificates) {
    this.id = id;
    this.price = price;
    this.createDate = createDate;
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
   * Sets price.
   *
   * @param price the price
   */
  public void setPrice(BigDecimal price) {
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
   * Gets certificates.
   *
   * @return the certificates
   */
  public List<CertificateDTO> getCertificates() {
    return certificates;
  }

  /**
   * Sets certificates.
   *
   * @param certificates the certificates
   */
  public void setCertificates(List<CertificateDTO> certificates) {
    this.certificates = certificates;
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
    OrderDTO orderDTO = (OrderDTO) o;
    return Objects.equals(id, orderDTO.id)
        && Objects.equals(price, orderDTO.price)
        && Objects.equals(createDate, orderDTO.createDate)
        && Objects.equals(certificates, orderDTO.certificates);
  }

  /**
   * Hash code int.
   *
   * @return the int
   */
  @Override
  public int hashCode() {
    return Objects.hash(id, price, createDate, certificates);
  }

  /**
   * To string string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return "OrderDTO{"
        + "id="
        + id
        + ", price="
        + price
        + ", createDate="
        + createDate
        + ", certificates="
        + certificates
        + '}';
  }
}
