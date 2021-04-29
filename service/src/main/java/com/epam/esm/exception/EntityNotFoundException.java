package com.epam.esm.exception;

/**
 * Entity not found exception. Throws if object not found
 *
 * @author Andrey Belik
 * @version 1.0
 */
public class EntityNotFoundException extends RuntimeException {
  /**
   * Constructor
   *
   * @param message message of the exception
   * @param entityClass the entity class
   */
  public EntityNotFoundException(String message, Class<?> entityClass) {
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

  /**
   * Constructor
   *
   * @param cause another exception
   */
}
