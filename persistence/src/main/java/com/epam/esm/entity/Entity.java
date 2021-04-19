package com.epam.esm.entity;

/**
 * @param <T> T - type of id
 * @author Andrey Belik
 * @version 1.0
 */
public abstract class Entity<T> {

  /** id for the object */
  private T id;

  /** default constructor */
  public Entity() {}

  /**
   * constructor
   *
   * @param id - id of the entity
   */
  public Entity(T id) {
    this.id = id;
  }

  /**
   * Id getter
   *
   * @return id of the entity
   */
  public T getId() {
    return id;
  }

  /**
   * Id setter
   *
   * @param id id of the entity
   */
  public void setId(T id) {
    this.id = id;
  }
}
