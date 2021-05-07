package com.epam.esm.validator.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.messages.Translator;
import com.epam.esm.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
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

  /** The constant TAG_NOT_NULL_. */
  private static final String TAG_NOT_NULL_KEY = "tag_validator.not_null";

  private static final String TAG_NAME_NOT_EMPTY_KEY = "tag_validator.name_not_empty";
  private static final String TAG_MIN_SIZE_KEY = "tag_validator.min_size_name";
  private static final String TAG_MAX_SIZE_KEY = "tag_validator.max_size_name";
  private static final String TAG_CHARACTERS_KEY = "validator.characters";
  /** The Translator. */
  private Translator translator;

  /** Instantiates a new Tag validator. */
  public TagValidator() {}

  /**
   * Instantiates a new Tag validator.
   *
   * @param translator the translator
   */
  @Autowired
  public TagValidator(Translator translator) {
    this.translator = translator;
  }

  /**
   * Validate.
   *
   * @param tag the tag
   * @throws ValidatorException the validator exception
   */
  @Override
  public void validate(Tag tag) throws ValidatorException {
    if (tag == null) {
      throw new ValidatorException(List.of(translator.toLocale(TAG_NOT_NULL_KEY)), Tag.class);
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
      throw new ValidatorException(List.of(TAG_NAME_NOT_EMPTY_KEY), Tag.class);
    }
    List<String> messages = new ArrayList<>();
    if (name.length() < MIN_NAME_SIZE) {
      messages.add(
          translator.toLocale(TAG_MIN_SIZE_KEY)
              + " "
              + MIN_NAME_SIZE
              + " "
              + translator.toLocale(TAG_CHARACTERS_KEY));
    }
    if (name.length() > MAX_NAME_SIZE) {
      messages.add(
          translator.toLocale(TAG_MAX_SIZE_KEY)
              + " "
              + MAX_NAME_SIZE
              + " "
              + translator.toLocale(TAG_CHARACTERS_KEY));
    }
    if (!messages.isEmpty()) {
      throw new ValidatorException(messages, Tag.class);
    }
  }
}
