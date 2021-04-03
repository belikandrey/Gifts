package com.epam.esm.dto.converter.impl;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagConverter implements Converter<Tag, TagDTO> {
    @Override
    public Tag convert(TagDTO dto) {
        return new Tag(dto.getId(), dto.getName());
    }

    @Override
    public TagDTO convert(Tag entity) {
        return new TagDTO(entity.getId(), entity.getName());
    }
}
