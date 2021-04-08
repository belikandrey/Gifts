package com.epam.esm.exception;

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
   * @param message message of the exception
   */
  public ValidatorException(String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param cause another exception
   */
  public ValidatorException(Throwable cause) {
    super(cause);
  }
}
