package com.epam.esm.validator.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.validator.Validator;
import org.springframework.stereotype.Service;

@Service
public class TagValidator implements Validator<Tag> {

    @Override
    public void validate(Tag tag) throws ValidatorException {
        if (tag == null) {
            throw new ValidatorException("Tag should be not null");
        }
        if (tag.getName() == null || tag.getName().isEmpty()) {
            throw new ValidatorException("Tag name should be not empty");
        }
        if (tag.getName().length() < 3) {
            throw new ValidatorException("Tag name should contain at least 3 characters");
        }
        if (tag.getName().length() > 30) {
            throw new ValidatorException("Tag name should contain not more than 30 characters");
        }
    }
}
