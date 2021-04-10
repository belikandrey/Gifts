package com.epam.esm.service;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityNotFoundException;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Set;

public interface TagService extends EntityService<TagDTO, BigInteger> {
    Collection<TagDTO> findAll();
    Set<TagDTO> findTagsByCertificateId(BigInteger id);
    TagDTO findByName(String name) throws EntityNotFoundException;

}
