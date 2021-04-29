package com.epam.esm.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigInteger;
import java.util.Objects;

/** Tag transfer class */
@Relation(collectionRelation = "tags")
public class TagDTO extends RepresentationModel<TagDTO> {
  /** The Id. */
  private BigInteger id;

  /** The Name. */
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
    TagDTO tagDTO = (TagDTO) o;
    return Objects.equals(id, tagDTO.id) && Objects.equals(name, tagDTO.name);
  }

  /**
   * Hash code int.
   *
   * @return the int
   */
  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }

  /**
   * To string string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return "TagDTO{" + "id=" + id + ", name='" + name + '\'' + '}';
  }
}
