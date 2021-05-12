package com.epam.esm.dto.converter.impl;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TagConverterTest {
  private static final String TAG_NAME = "#cool";
  private static final BigInteger TAG_ID = BigInteger.ONE;
  private static final String TAG_DTO_NAME = "#beautiful";
  private static final BigInteger TAG_DTO_ID = BigInteger.TEN;
  TagConverter tagConverter = new TagConverter();
  private static final Tag TAG = new Tag(TAG_ID, TAG_NAME);
  private static final TagDTO TAG_DTO = new TagDTO(TAG_DTO_ID, TAG_DTO_NAME);

  @Test
  void testConvertToEntity() {
    Tag result = tagConverter.convertToEntity(TAG_DTO);
    assertNotNull(result);
    assertEquals(TAG_DTO_NAME, result.getName());
    assertEquals(TAG_DTO_ID, result.getId());
  }

  @Test
  void testConvertToDto() {
    TagDTO result = tagConverter.convertToDto(TAG);
    assertNotNull(result);
    assertEquals(TAG_ID, result.getId());
    assertEquals(TAG_NAME, result.getName());
  }
}
