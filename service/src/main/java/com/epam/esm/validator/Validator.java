package com.epam.esm.validator;

import com.epam.esm.exception.ValidatorException;

/**
 * The interface Validator.
 *
 * @param <T> the type parameter
 */
public interface Validator<T> {
  /**
   * Validate.
   *
   * @param t the t
   * @throws ValidatorException the validator exception
   */
  void validate(T t) throws ValidatorException;
}
