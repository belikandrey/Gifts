package com.epam.esm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;

/**
 * The type Tag.
 *
 * @author Andrey Belik
 * @version 1.0
 * @see
 */
@Entity
@Table(name = "tag")
public class Tag implements Serializable {

  /** The Id. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private BigInteger id;

  /** The Name. */
  @Column(name = "name")
  private String name;

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
    Tag tag = (Tag) o;
    return Objects.equals(name, tag.name);
  }

  /**
   * Hash code int.
   *
   * @return the int
   */
  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  /**
   * To string string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return "Tag{" + "name='" + name + '\'' + '}';
  }
}
