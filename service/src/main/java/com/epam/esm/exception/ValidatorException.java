package com.epam.esm.exception;

import java.util.List;

/**
 * Validation exception. Throws if object is invalid
 *
 * @author Andrey Belik
 * @version 1.0
 */
public class ValidatorException extends RuntimeException {
  /**
   * Constructor
   *
   * <p>//@param message message of the exception
   *
   * @param messages the messages
   * @param entityClass the entity class
   */
  public ValidatorException(List<String> messages, Class<?> entityClass) {
    this.messages = messages;
    this.entityClass = entityClass;
  }

  /**
   * Instantiates a new Validator exception.
   *
   * @param message the message
   * @param entityClass the entity class
   */
  public ValidatorException(String message, Class<?> entityClass) {
    super(message);
    this.entityClass = entityClass;
  }

  /** The Messages. */
  private List<String> messages;

  /** The Entity class. */
  private Class<?> entityClass;

  /**
   * Gets messages.
   *
   * @return the messages
   */
  public List<String> getMessages() {
    return messages;
  }

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
