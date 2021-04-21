package com.epam.esm.validator;

import com.epam.esm.exception.ValidatorException;

public interface Validator<T> {
  void validate(T t) throws ValidatorException;
}
