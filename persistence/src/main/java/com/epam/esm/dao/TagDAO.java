package com.epam.esm.dao;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityNotFoundException;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Set;

public interface TagDAO extends AbstractDAO<Tag, BigInteger> {
    Collection<Tag> findAll();
    Set<Tag> findTagsByCertificateId(BigInteger certificateId);
    boolean isAlreadyExist(Tag tag);
    Tag findTagByName(String name) throws EntityNotFoundException;
}
