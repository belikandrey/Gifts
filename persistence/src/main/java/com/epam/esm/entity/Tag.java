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
import java.math.BigInteger;
import java.util.Objects;
import java.util.Set;

/**
 * @author Andrey Belik
 * @version 1.0
 * @see
 */
@Entity
@Table(name = "tag")
public class Tag {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private BigInteger id;

  @Column(name = "name")
  private String name;

  @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinTable(
      name = "certificate_tag",
      joinColumns = @JoinColumn(name = "tag_id"),
      inverseJoinColumns = @JoinColumn(name = "certificate_id"))
  private Set<Certificate> certificate;

  /**
   * Constructor
   *
   * @param name name of the tag
   */
  public Tag(String name) {
    this.name = name;
  }

  /** Default constructor */
  public Tag() {}

  /**
   * Constructor
   *
   * @param id id of the tag
   * @param name name of the tag
   */
  public Tag(BigInteger id, String name) {
    this.id = id;
    this.name = name;
  }

  public Set<Certificate> getCertificate() {
    return certificate;
  }

  public void setCertificate(Set<Certificate> certificate) {
    this.certificate = certificate;
  }

  public BigInteger getId() {
    return id;
  }

  public void setId(BigInteger id) {
    this.id = id;
  }

  /**
   * Name getter
   *
   * @return name of the tag
   */
  public String getName() {
    return name;
  }

  /**
   * Name setter
   *
   * @param name name of the tag
   */
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Tag tag = (Tag) o;
    return Objects.equals(name, tag.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public String toString() {
    return "Tag{" + "name='" + name + '\'' + '}';
  }
}
