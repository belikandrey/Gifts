package com.epam.esm.dto;

import java.math.BigInteger;
import java.util.Objects;

/** Tag transfer class */
public class TagDTO {
  private BigInteger id;
  private String name;

  /**
   * Constructor
   *
   * @param id id of the tag DTO
   * @param name name of the tag DTO
   */
  public TagDTO(BigInteger id, String name) {
    this.id = id;
    this.name = name;
  }

  /** Default constructor */
  public TagDTO() {}

  /**
   * Id getter
   *
   * @return id of the tag DTO
   */
  public BigInteger getId() {
    return id;
  }

  /**
   * Id setter
   *
   * @param id id of the tag DTO
   */
  public void setId(BigInteger id) {
    this.id = id;
  }

  /**
   * Name getter
   *
   * @return name of tag DTO
   */
  public String getName() {
    return name;
  }

  /**
   * Name setter
   *
   * @param name name of the tag DTO
   */
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TagDTO tagDTO = (TagDTO) o;
    return Objects.equals(id, tagDTO.id) && Objects.equals(name, tagDTO.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }

  @Override
  public String toString() {
    return "TagDTO{" + "id=" + id + ", name='" + name + '\'' + '}';
  }
}
