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
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * @author Andrey Belik
 * @version 1.0
 * @see
 */
@Entity
@Table(name = "certificate")
public class Certificate implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private BigInteger id;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "price")
  private BigDecimal price;

  @Column(name = "duration")
  private Integer duration;

  @Column(name = "createDate")
  private LocalDateTime createDate;

  @Column(name = "lastUpdateDate")
  private LocalDateTime lastUpdateDate;

  @Column(name = "is_enabled")
  private Boolean isEnabled;

  @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinTable(
      name = "certificate_tag",
      joinColumns = @JoinColumn(name = "certificate_id"),
      inverseJoinColumns = @JoinColumn(name = "tag_id"))
  private Set<Tag> tags;

  /**
   * Constructor
   *
   * @param id id of the certificate
   */
  public Certificate(BigInteger id) {
    this.id = id;
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
    this.id = id;
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

  public BigInteger getId() {
    return id;
  }

  public void setId(BigInteger id) {
    this.id = id;
  }

  /**
   * Name setter
   *
   * @param name name of the certificate
   */
  public void setName(String name) {
    this.name = name;
  }

  public Set<Tag> getTags() {
    return tags;
  }

  public void setTags(Set<Tag> tags) {
    this.tags = tags;
  }

  public Boolean getEnabled() {
    return isEnabled;
  }

  public void setEnabled(Boolean enabled) {
    isEnabled = enabled;
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
        + ", isEnabled="
        + isEnabled
        + '}';
  }
}
