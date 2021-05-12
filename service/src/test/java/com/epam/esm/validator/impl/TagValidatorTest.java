package com.epam.esm.validator.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ValidatorException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TagValidatorTest {
  private static TagValidator tagValidator;
  private static final String SMALL_SIZE_NAME = "A";
  private static final String BIG_SIZE_NAME = "SOME_TAG_NAME_WITH_BIG_NAME_VERY_BIG_NAME";
  private static final String NORMAL_SIZE_NAME = "#Cool";
  private static final String SMALL_SIZE_EXCEPTION_MESSAGE =
      "Tag name should contain at least 3 characters";
  private static final String BIG_SIZE_EXCEPTION_MESSAGE =
      "Tag name should contain not more than 30 characters";
  private static final String EMPTY_NAME_EXCEPTION_MESSAGE = "Tag name should be not empty";
  private static final String NULL_TAG_EXCEPTION_MESSAGE = "Tag should be not null";

  @BeforeAll
  public static void init() {
    tagValidator = new TagValidator(null);
  }

  // Tag with small size name given. Expected ValidationException with message "Tag name should
  // contain at least 3 characters"
  @Test
  public void smallSizeTagNameTest() {
    Tag tagWithSmallName = new Tag(SMALL_SIZE_NAME);
    final ValidatorException exception =
        assertThrows(ValidatorException.class, () -> tagValidator.validate(tagWithSmallName));
    assertEquals(exception.getMessages().get(0), SMALL_SIZE_EXCEPTION_MESSAGE);
  }

  // Tag with big size name given. Expected ValidationException with message "Tag name should
  // contain not more than 30 characters"
  @Test
  public void bigSizeTagNameTest() {
    Tag tagWithBigName = new Tag(BIG_SIZE_NAME);
    final ValidatorException exception =
        assertThrows(ValidatorException.class, () -> tagValidator.validate(tagWithBigName));
    assertEquals(exception.getMessages().get(0), BIG_SIZE_EXCEPTION_MESSAGE);
  }

  // Tag with empty name given. Expected ValidationException with message "Tag name should be not
  // empty"
  @Test
  public void emptyTagNameTest() {
    Tag tagWithEmptyName = new Tag("");
    final ValidatorException exception =
        assertThrows(ValidatorException.class, () -> tagValidator.validate(tagWithEmptyName));
    assertEquals(exception.getMessages().get(0), EMPTY_NAME_EXCEPTION_MESSAGE);
  }

  // Tag with right name given. Expected work without exception
  @Test
  public void rightTagNameTest() {
    Tag tagWithRightName = new Tag(NORMAL_SIZE_NAME);
    assertDoesNotThrow(() -> tagValidator.validate(tagWithRightName));
  }

  // Null given. Expected ValidationException with message "Tag should be not null"
  @Test
  public void nullTagTest() {
    final ValidatorException exception =
        assertThrows(ValidatorException.class, () -> tagValidator.validate(null));
    assertEquals(exception.getMessages().get(0), NULL_TAG_EXCEPTION_MESSAGE);
  }
}