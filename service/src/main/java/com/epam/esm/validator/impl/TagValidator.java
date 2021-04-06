package com.epam.esm.validator.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.validator.Validator;
import org.springframework.stereotype.Service;

@Service
public class TagValidator implements Validator<Tag> {

    private final int MIN_NAME_SIZE = 2;
    private final int MAX_NAME_SIZE = 35;

    @Override
    public void validate(Tag tag) throws ValidatorException {
        if (tag == null) {
            throw new ValidatorException("Tag should be not null");
        }
        validateName(tag.getName());
    }

    private void validateName(String name) throws ValidatorException {
        if (name == null || name.isEmpty()) {
            throw new ValidatorException("Tag name should be not empty");
        }
        if (name.length() < MIN_NAME_SIZE) {
            throw new ValidatorException("Tag name should contain at least 3 characters");
        }
        if (name.length() > MAX_NAME_SIZE) {
            throw new ValidatorException("Tag name should contain not more than 30 characters");
        }
    }

}
