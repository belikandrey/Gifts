package com.epam.esm.service;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;

import java.math.BigInteger;
import java.util.Collection;

public interface TagService extends EntityService<TagDTO, BigInteger> {
    Collection<TagDTO> findAll();

}
