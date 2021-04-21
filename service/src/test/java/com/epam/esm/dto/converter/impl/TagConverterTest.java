package com.epam.esm.dto.converter.impl;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class TagConverterTest {
/*
  private static TagConverter tagConverter;
  private static final String NAME = "#cool";
  private static final BigInteger ID = BigInteger.ONE;

  @BeforeAll
  public static void init() {
    tagConverter = new TagConverter();
  }

  @Test
  public void convertToDtoTest() {
    Tag tag = new Tag(ID, NAME);
    final TagDTO tagDTO = tagConverter.convert(tag);
    assertNotNull(tagDTO);
    assertEquals(tag.getId(), tagDTO.getId());
    assertEquals(tag.getName(), tagDTO.getName());
  }

  @Test
  public void convertToEntityTest() {
    TagDTO tagDTO = new TagDTO(ID, NAME);
    final Tag tag = tagConverter.convert(tagDTO);
    assertNotNull(tag);
    assertEquals(tag.getId(), tagDTO.getId());
    assertEquals(tag.getName(), tagDTO.getName());
  }*/
}
