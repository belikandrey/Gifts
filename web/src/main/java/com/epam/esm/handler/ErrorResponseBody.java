package com.epam.esm.handler;

import java.util.List;

/** The type Error response body. */
public class ErrorResponseBody {

  /** The Error messages. */
  private List<String> errorMessages;

  /** The Code. */
  private String errorCode;

  /**
   * Instantiates a new Error response body.
   *
   * @param errorMessages the error messages
   * @param code the code
   */
  public ErrorResponseBody(List<String> errorMessages, String code) {
    this.errorMessages = errorMessages;
    this.errorCode = code;
  }

  /**
   * Gets messages.
   *
   * @return the messages
   */
  public List<String> getErrorMessages() {
    return errorMessages;
  }

  /**
   * Sets messages.
   *
   * @param messages the messages
   */
  public void setErrorMessages(List<String> messages) {
    this.errorMessages = messages;
  }

  /**
   * Gets code.
   *
   * @return the code
   */
  public String getErrorCode() {
    return errorCode;
  }

  /**
   * Sets code.
   *
   * @param code the code
   */
  public void setErrorCode(String code) {
    this.errorCode = code;
  }
}
