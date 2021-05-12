package com.epam.esm.validator.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.messages.Translator;
import com.epam.esm.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
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

  private static final String CERTIFICATE_NOT_NULL_KEY = "certificate_validator.not_null";
  private static final String CERTIFICATE_CHARACTERS_KEY = "validator.characters";
  private static final String CERTIFICATE_NAME_MIN_KEY = "certificate_validator.min_size_name";
  private static final String CERTIFICATE_NAME_MAX_KEY = "certificate_validator.max_size_name";
  private static final String CERTIFICATE_NAME_NOT_EMPTY_KEY =
      "certificate_validator.name_not_empty";
  private static final String CERTIFICATE_DESCRIPTION_NOT_EMPTY_KEY =
      "certificate_validator.description_not_empty";
  private static final String CERTIFICATE_DESCRIPTION_MAX_SIZE_KEY =
      "certificate_validator.description_max_size";
  private static final String CERTIFICATE_DURATION_MORE_THAN_ZERO_KEY =
      "certificate_validator.duration_more_than_0";
  private static final String CERTIFICATE_PRICE_NOT_NULL_KEY =
      "certificate_validator.price_not_null";
  private static final String CERTIFICATE_PRICE_MORE_THAN_ZERO_KEY =
      "certificate_validator.price_more_than_0";

  private Translator translator;

  @Autowired
  public CertificateValidator(Translator translator) {
    this.translator = translator;
  }

  /**
   * Validate.
   *
   * @param certificate the certificate
   * @throws ValidatorException the validator exception
   */
  @Override
  public void validate(Certificate certificate) throws ValidatorException {

    if (certificate == null) {
      throw new ValidatorException(
          List.of(translator.toLocale(CERTIFICATE_NOT_NULL_KEY)), Certificate.class);
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
      return translator.toLocale(CERTIFICATE_NAME_NOT_EMPTY_KEY);
    }
    if (name.length() < MIN_NAME_SIZE) {
      return translator.toLocale(
          CERTIFICATE_NAME_MIN_KEY
              + " "
              + MIN_NAME_SIZE
              + " "
              + translator.toLocale(CERTIFICATE_CHARACTERS_KEY));
    }
    return name.length() > MAX_NAME_SIZE
        ? translator.toLocale(CERTIFICATE_NAME_MAX_KEY)
            + " "
            + MAX_NAME_SIZE
            + " "
            + translator.toLocale(CERTIFICATE_CHARACTERS_KEY)
        : null;
  }

  /**
   * Validate description string.
   *
   * @param description the description
   * @return the string
   */
  private String validateDescription(String description) {
    if (description == null || description.isEmpty()) {
      return translator.toLocale(CERTIFICATE_DESCRIPTION_NOT_EMPTY_KEY);
    }
    return description.length() > MAX_DESCRIPTION_SIZE
        ? translator.toLocale(CERTIFICATE_DESCRIPTION_MAX_SIZE_KEY)
            + " "
            + MAX_DESCRIPTION_SIZE
            + " "
            + translator.toLocale(CERTIFICATE_CHARACTERS_KEY)
        : null;
  }

  /**
   * Validate duration string.
   *
   * @param duration the duration
   * @return the string
   */
  private String validateDuration(Integer duration) {
    return duration == null || duration <= 0
        ? translator.toLocale(CERTIFICATE_DURATION_MORE_THAN_ZERO_KEY)
        : null;
  }

  /**
   * Validate price string.
   *
   * @param price the price
   * @return the string
   */
  private String validatePrice(BigDecimal price) {
    if (price == null) {
      return translator.toLocale(CERTIFICATE_PRICE_NOT_NULL_KEY);
    }
    return price.compareTo(BigDecimal.ZERO) < 0
        ? translator.toLocale(CERTIFICATE_PRICE_MORE_THAN_ZERO_KEY)
        : null;
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
