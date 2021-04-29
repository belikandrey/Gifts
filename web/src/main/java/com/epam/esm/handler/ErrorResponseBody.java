package com.epam.esm.handler;

import java.util.List;

/** The type Error response body. */
public class ErrorResponseBody {

  /** The Error messages. */
  private List<String> errorMessages;

  /** The Code. */
  private String code;

  /**
   * Instantiates a new Error response body.
   *
   * @param errorMessages the error messages
   * @param code the code
   */
  public ErrorResponseBody(List<String> errorMessages, String code) {
    this.errorMessages = errorMessages;
    this.code = code;
  }

  /**
   * Gets messages.
   *
   * @return the messages
   */
  public List<String> getMessages() {
    return errorMessages;
  }

  /**
   * Sets messages.
   *
   * @param messages the messages
   */
  public void setMessages(List<String> messages) {
    this.errorMessages = messages;
  }

  /**
   * Gets code.
   *
   * @return the code
   */
  public String getCode() {
    return code;
  }

  /**
   * Sets code.
   *
   * @param code the code
   */
  public void setCode(String code) {
    this.code = code;
  }
}
