package com.epam.esm.handler;

import java.util.List;

public class ErrorResponseBody {

  private List<String> errorMessages;
  private String code;

  public ErrorResponseBody(List<String> errorMessages, String code) {
    this.errorMessages = errorMessages;
    this.code = code;
  }

  public List<String> getMessages() {
    return errorMessages;
  }

  public void setMessages(List<String> messages) {
    this.errorMessages = messages;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }
}
