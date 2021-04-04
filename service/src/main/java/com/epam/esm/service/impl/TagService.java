package com.epam.esm.service.impl;

import com.epam.esm.dao.AbstractDAO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.service.EntityService;
import com.epam.esm.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class TagService implements EntityService<TagDTO, BigInteger> {
    private final Validator<Tag> validator;
    private final AbstractDAO<Tag, BigInteger> tagDao;
    private final Converter<Tag, TagDTO> converter;

    @Autowired
    public TagService(Validator<Tag> validator, AbstractDAO<Tag, BigInteger> tagDao, Converter<Tag, TagDTO> converter) {
        this.validator = validator;
        this.tagDao = tagDao;
        this.converter = converter;
    }

    @Override
    public Collection<TagDTO> findAll() {
        return tagDao.findAll().stream().map(converter::convert).collect(Collectors.toList());
    }

    @Override
    public Collection<TagDTO> findAll(BigInteger id) {
        return tagDao.findAll(id).stream().map(converter::convert).collect(Collectors.toList());
    }

    @Override
    public TagDTO find(BigInteger id) {
        final Tag tag = tagDao.find(id);
        return tag != null ? converter.convert(tag) : null;
    }


    @Override
    public TagDTO add(TagDTO tagDTO) throws ValidatorException {
        Tag tag = converter.convert(tagDTO);
        validator.validate(tag);
        return tagDao.add(tag) > 0 ? tagDTO : null;
    }

    @Override
    public int update(BigInteger id, TagDTO tagDTO) throws ValidatorException {
        Tag tag = converter.convert(tagDTO);
        validator.validate(tag);
        return tagDao.update(id, tag);
    }

    @Override
    public int delete(BigInteger id) {
        return tagDao.delete(id);
    }
}
