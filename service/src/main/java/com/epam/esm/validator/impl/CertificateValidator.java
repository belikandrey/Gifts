package com.epam.esm.validator.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.validator.Validator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** The type Certificate validator. */
@Service
public class CertificateValidator implements Validator<Certificate> {

  /** The Min name size. */
  private final int MIN_NAME_SIZE = 3;

  /** The Max name size. */
  private final int MAX_NAME_SIZE = 120;

  /** The Max description size. */
  private final int MAX_DESCRIPTION_SIZE = 150;

  /**
   * Validate.
   *
   * @param certificate the certificate
   * @throws ValidatorException the validator exception
   */
  @Override
  public void validate(Certificate certificate) throws ValidatorException {

    if (certificate == null) {
      throw new ValidatorException(List.of("Certificate should be not null"), Certificate.class);
    }
    List<String> messages = new ArrayList<>();
    addToMessages(messages, validateName(certificate.getName()));
    addToMessages(messages, validateDescription(certificate.getDescription()));
    addToMessages(messages, validateDuration(certificate.getDuration()));
    addToMessages(messages, validatePrice(certificate.getPrice()));
    if (!messages.isEmpty()) {
      throw new ValidatorException(messages, Certificate.class);
    }
  }

  /**
   * Validate name string.
   *
   * @param name the name
   * @return the string
   */
  private String validateName(String name) {
    if (name == null || name.isEmpty()) {
      return "Certificate name should be not empty";
    }
    if (name.length() < MIN_NAME_SIZE) {
      return "Certificate name should be at least " + MIN_NAME_SIZE + " characters";
    }
    if (name.length() > MAX_NAME_SIZE) {
      return "Certificate name should be not more than " + MAX_NAME_SIZE + " characters";
    }
    return null;
  }

  /**
   * Validate description string.
   *
   * @param description the description
   * @return the string
   */
  private String validateDescription(String description) {
    if (description == null || description.isEmpty()) {
      return "Certificate description should be not empty";
    }
    if (description.length() > MAX_DESCRIPTION_SIZE) {
      return "Certificate description should be not more than "
          + MAX_DESCRIPTION_SIZE
          + " characters";
    }
    return null;
  }

  /**
   * Validate duration string.
   *
   * @param duration the duration
   * @return the string
   */
  private String validateDuration(Integer duration) {
    if (duration == null || duration <= 0) {
      return "Certificate duration should be more than 0";
    }
    return null;
  }

  /**
   * Validate price string.
   *
   * @param price the price
   * @return the string
   */
  private String validatePrice(BigDecimal price) {
    if (price == null) {
      return "Certificate price should be not null";
    }
    if (price.compareTo(BigDecimal.ZERO) < 0) {
      return "Certificate price should be more than or equals 0";
    }
    return null;
  }

  /**
   * Add to messages.
   *
   * @param message the message
   * @param newMessage the new message
   */
  private void addToMessages(List<String> message, String newMessage) {
    if (newMessage != null) {
      message.add(newMessage);
    }
  }
}
