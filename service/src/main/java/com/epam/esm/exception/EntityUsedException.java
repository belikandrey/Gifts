package com.epam.esm.exception;

/** The type Entity used exception. */
public class EntityUsedException extends RuntimeException {
  /** Instantiates a new Entity used exception. */
  public EntityUsedException() {}

  /**
   * Instantiates a new Entity used exception.
   *
   * @param message the message
   * @param entityClass the entity class
   */
  public EntityUsedException(String message, Class<?> entityClass) {
    super(message);
    this.entityClass = entityClass;
  }

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
}
