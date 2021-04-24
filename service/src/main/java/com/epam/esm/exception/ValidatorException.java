package com.epam.esm.exception;

import java.util.List;

/**
 * Validation exception. Throws if object is invalid
 *
 * @author Andrey Belik
 * @version 1.0
 */
public class ValidatorException extends Exception {
  /**
   * Constructor
   *
   * <p>//@param message message of the exception
   *
   * @param messages
   */
  public ValidatorException(List<String> messages, Class<?> entityClass) {
    this.messages = messages;
    this.entityClass = entityClass;
  }

  public ValidatorException(String message, Class<?> entityClass) {
    super(message);
    this.entityClass = entityClass;
  }

  private List<String> messages;

  private Class<?> entityClass;

  public List<String> getMessages() {
    return messages;
  }

  public Class<?> getEntityClass() {
    return entityClass;
  }

  /**
   * Constructor
   *
   * @param cause another exception
   */
}
