package com.epam.esm.validator.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.validator.Validator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/** The type Tag validator. */
@Service
public class TagValidator implements Validator<Tag> {

  /** The Min name size. */
  private final int MIN_NAME_SIZE = 2;

  /** The Max name size. */
  private final int MAX_NAME_SIZE = 35;

  /**
   * Validate.
   *
   * @param tag the tag
   * @throws ValidatorException the validator exception
   */
  @Override
  public void validate(Tag tag) throws ValidatorException {
    if (tag == null) {
      throw new ValidatorException(List.of("Tag should be not null"), Tag.class);
    }
    validateName(tag.getName());
  }

  /**
   * Validate name.
   *
   * @param name the name
   * @throws ValidatorException the validator exception
   */
  private void validateName(String name) throws ValidatorException {
    if (name == null || name.isEmpty()) {
      throw new ValidatorException(List.of("Tag name should be not empty"), Tag.class);
    }
    List<String> messages = new ArrayList<>();
    if (name.length() < MIN_NAME_SIZE) {
      messages.add("Tag name should contain at least 3 characters");
    }
    if (name.length() > MAX_NAME_SIZE) {
      messages.add("Tag name should contain not more than 30 characters");
    }
    if (!messages.isEmpty()) {
      throw new ValidatorException(messages, Tag.class);
    }
  }
}
