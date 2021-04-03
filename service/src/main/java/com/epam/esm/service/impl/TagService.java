package com.epam.esm.service.impl;

import com.epam.esm.dao.AbstractDAO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.service.EntityService;
import com.epam.esm.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TagService implements EntityService<Tag> {
    private Validator<Tag> validator;
    private AbstractDAO<Tag> tagDao;

    @Autowired
    public TagService(Validator<Tag> validator, AbstractDAO<Tag> tagDao) {
        this.validator = validator;
        this.tagDao = tagDao;
    }

    @Override
    public Collection<Tag> findAll() {
        return tagDao.findAll();
    }

    @Override
    public Collection<Tag> findAll(int id) {
        return tagDao.findAll(id);
    }

    @Override
    public Tag find(int id) {
        return tagDao.find(id);
    }

    @Override
    public Tag add(Tag tag) throws ValidatorException {
        validator.validate(tag);
        tagDao.add(tag);
        return tag;
    }

    @Override
    public void update(int id, Tag tag) throws ValidatorException {
        validator.validate(tag);
        tagDao.update(id, tag);
    }

    @Override
    public void delete(int id) {
        tagDao.delete(id);
    }
}
