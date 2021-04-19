package com.epam.esm.entity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Andrey Belik
 * @version 1.0
 * @see Entity
 */
public class Certificate extends Entity<BigInteger> {

  private String name;

  private String description;

  private BigDecimal price;

  private Integer duration;

  private LocalDateTime createDate;

  private LocalDateTime lastUpdateDate;

  /**
   * Constructor
   *
   * @param id id of the certificate
   */
  public Certificate(BigInteger id) {
    super(id);
  }

  /** Default constructor */
  public Certificate() {}

  /**
   * Constructor
   *
   * @param id id of the certificate
   * @param name name of the certificate
   * @param description description of the certificate
   * @param price price of the certificate
   * @param duration duration of the certificate
   * @param createDate create date of the certificate
   * @param lastUpdateDate last update date of the certificate
   */
  public Certificate(
      BigInteger id,
      String name,
      String description,
      BigDecimal price,
      Integer duration,
      LocalDateTime createDate,
      LocalDateTime lastUpdateDate) {
    super(id);
    this.name = name;
    this.description = description;
    this.price = price;
    this.duration = duration;
    this.createDate = createDate;
    this.lastUpdateDate = lastUpdateDate;
  }

  /**
   * Name getter
   *
   * @return name of the certificate
   */
  public String getName() {
    return name;
  }

  /**
   * Name setter
   *
   * @param name name of the certificate
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Description getter
   *
   * @return description of the certificate
   */
  public String getDescription() {
    return description;
  }

  /**
   * Description setter
   *
   * @param description description of the certificate
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Price getter
   *
   * @return price of the certificate
   */
  public BigDecimal getPrice() {
    return price;
  }

  /**
   * Price setter
   *
   * @param price price of the certificate
   */
  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  /**
   * Duration getter
   *
   * @return duration of the certificate
   */
  public Integer getDuration() {
    return duration;
  }

  /**
   * Duration setter
   *
   * @param duration duration of the certificate
   */
  public void setDuration(Integer duration) {
    this.duration = duration;
  }

  /**
   * Create date getter
   *
   * @return create date of the certificate
   */
  public LocalDateTime getCreateDate() {
    return createDate;
  }

  /**
   * Create date setter
   *
   * @param createDate create date of the certificate
   */
  public void setCreateDate(LocalDateTime createDate) {
    this.createDate = createDate;
  }

  /**
   * Last update date getter
   *
   * @return last update date of the certificate
   */
  public LocalDateTime getLastUpdateDate() {
    return lastUpdateDate;
  }

  /**
   * Last update date setter
   *
   * @param lastUpdateDate last update date of the certificate
   */
  public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
    this.lastUpdateDate = lastUpdateDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Certificate that = (Certificate) o;
    return duration == that.duration
        && Objects.equals(name, that.name)
        && Objects.equals(description, that.description)
        && Objects.equals(price, that.price)
        && Objects.equals(createDate, that.createDate)
        && Objects.equals(lastUpdateDate, that.lastUpdateDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description, price, duration, createDate, lastUpdateDate);
  }

  @Override
  public String toString() {
    return "GiftCertificate{"
        + "name='"
        + name
        + '\''
        + ", description='"
        + description
        + '\''
        + ", price="
        + price
        + ", duration="
        + duration
        + ", createDate="
        + createDate
        + ", lastUpdateDate="
        + lastUpdateDate
        + '}';
  }
}
