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
   * @param dto {@link TagDTO} to convert
   * @return {@link Tag} after convert
   */
  @Override
  public Tag convertToEntity(TagDTO dto) {
    return new Tag(dto.getId(), dto.getName());
  }

  /**
   * Convert tag to tag DTO
   *
   * @param tag {@link Tag} to convert
   * @return {@link TagDTO} after convert
   */
  @Override
  public TagDTO convertToDto(Tag tag) {
    return new TagDTO(tag.getId(), tag.getName());
  }
}
