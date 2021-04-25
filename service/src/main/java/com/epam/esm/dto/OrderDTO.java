package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class OrderDTO extends RepresentationModel<OrderDTO> {

  private BigInteger id;

  private BigDecimal price;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private LocalDateTime createDate;

  private List<CertificateDTO> certificates;



  public OrderDTO(
      BigInteger id, BigDecimal price, LocalDateTime createDate, List<CertificateDTO> certificates) {
    this.id = id;
    this.price = price;
    this.createDate = createDate;
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

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public LocalDateTime getCreateDate() {
    return createDate;
  }

  public void setCreateDate(LocalDateTime createDate) {
    this.createDate = createDate;
  }

  public List<CertificateDTO> getCertificates() {
    return certificates;
  }

  public void setCertificates(List<CertificateDTO> certificates) {
    this.certificates = certificates;
  }

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

  @Override
  public int hashCode() {
    return Objects.hash(id, price, createDate, certificates);
  }

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
