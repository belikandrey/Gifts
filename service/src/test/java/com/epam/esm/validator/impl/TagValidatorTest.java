package com.epam.esm.validator.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ValidatorException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TagValidatorTest {
    private static TagValidator tagValidator;
    private final static String SMALL_SIZE_NAME = "A";
    private final static String BIG_SIZE_NAME = "SOME_TAG_NAME_WITH_BIG_NAME_VERY_BIG_NAME";
    private final static String NORMAL_SIZE_NAME = "#Cool";
    private final static String SMALL_SIZE_EXCEPTION_MESSAGE = "Tag name should contain at least 3 characters";
    private final static String BIG_SIZE_EXCEPTION_MESSAGE = "Tag name should contain not more than 30 characters";
    private final static String EMPTY_NAME_EXCEPTION_MESSAGE = "Tag name should be not empty";
    private final static String NULL_TAG_EXCEPTION_MESSAGE = "Tag should be not null";
    @BeforeAll
    public static void init(){
        tagValidator = new TagValidator();
    }

    @Test
    public void smallSizeTagNameTest(){
        Tag tagWithSmallName = new Tag(SMALL_SIZE_NAME);
        final ValidatorException exception = assertThrows(ValidatorException.class, ()->tagValidator.validate(tagWithSmallName));
        assertEquals(exception.getMessage(), SMALL_SIZE_EXCEPTION_MESSAGE);
    }

    @Test
    public void bigSizeTagNameTest(){
        Tag tagWithBigName = new Tag(BIG_SIZE_NAME);
        final ValidatorException exception = assertThrows(ValidatorException.class, () -> tagValidator.validate(tagWithBigName));
        assertEquals(exception.getMessage(), BIG_SIZE_EXCEPTION_MESSAGE);
    }

    @Test
    public void emptyTagNameTest(){
        Tag tagWithEmptyName = new Tag("");
        final ValidatorException exception = assertThrows(ValidatorException.class, () -> tagValidator.validate(tagWithEmptyName));
        assertEquals(exception.getMessage(), EMPTY_NAME_EXCEPTION_MESSAGE);
    }

    @Test
    public void rightTagNameTest(){
        Tag tagWithRightName = new Tag(NORMAL_SIZE_NAME);
        assertDoesNotThrow(()->tagValidator.validate(tagWithRightName));
    }

    @Test
    public void nullTagTest(){
        final ValidatorException exception = assertThrows(ValidatorException.class, () -> tagValidator.validate(null));
        assertEquals(exception.getMessage(), NULL_TAG_EXCEPTION_MESSAGE);
    }
}