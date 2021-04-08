package com.epam.esm.dto.converter.impl;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Component;

/**
 * Converter of tag
 *
 * @author Andrey Belik
 * @version 1.0
 */
@Component
public class TagConverter implements Converter<Tag, TagDTO> {
  /**
   * Convert tag DTO to tag
   *
   * @param dto {@link com.epam.esm.dto.TagDTO} to convert
   * @return {@link com.epam.esm.entity.Tag} after convert
   */
  @Override
  public Tag convert(TagDTO dto) {
    return new Tag(dto.getId(), dto.getName());
  }

  /**
   * Convert tag to tag DTO
   *
   * @param tag {@link com.epam.esm.entity.Tag} to convert
   * @return {@link com.epam.esm.dto.TagDTO} after convert
   */
  @Override
  public TagDTO convert(Tag tag) {
    return new TagDTO(tag.getId(), tag.getName());
  }
}
