package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Set;

public interface TagDAO extends AbstractDAO<Tag, BigInteger> {
    Collection<Tag> findAll();
    boolean isAlreadyExist(Tag tag);
}
