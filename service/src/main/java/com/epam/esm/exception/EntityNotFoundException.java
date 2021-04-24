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
   */
  public EntityNotFoundException(String message, Class<?> entityClass) {
    super(message);
    this.entityClass = entityClass;
  }

  private Class<?> entityClass;

  public Class<?> getEntityClass() {
    return entityClass;
  }

  /**
   * Constructor
   *
   * @param cause another exception
   */
}
