package com.epam.esm.exception;

/** The type Entity disabled exception. */
public class EntityDisabledException extends RuntimeException {

  /** The Entity class. */
  private Class<?> entityClass;

  /**
   * Gets entity class.
   *
   * @return the entity class
   */
  public Class<?> getEntityClass() {
    return entityClass;
  }

  /**
   * Instantiates a new Entity disabled exception.
   *
   * @param message the message
   * @param entityClass the entity class
   */
  public EntityDisabledException(String message, Class<?> entityClass) {
    super(message);
    this.entityClass = entityClass;
  }
}
