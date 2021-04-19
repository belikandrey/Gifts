package com.epam.esm.validator;

import com.epam.esm.entity.Entity;
import com.epam.esm.exception.ValidatorException;

public interface Validator<T extends Entity> {
  void validate(T t) throws ValidatorException;
}
