package com.epam.esm.exception;

/**
 * Entity already exist exception. Throws if object already exists in database
 *
 * @author Andrey Belik
 * @version 1.0
 */
public class EntityAlreadyExistException extends RuntimeException {
  /**
   * Constructor
   *
   * @param message message of the exception
   */
  public EntityAlreadyExistException(String message, Class<?> entityClass) {
    super(message);
    this.entityClass = entityClass;
  }
  /**
   * Constructor
   *
   * @param cause another exception
   */
  private Class<?> entityClass;

  public Class<?> getEntityClass() {
    return entityClass;
  }
}
