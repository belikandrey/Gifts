package com.epam.esm.validator.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.validator.Validator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CertificateValidator implements Validator<Certificate> {

    private final int MIN_NAME_SIZE = 3;
    private final int MAX_NAME_SIZE = 30;

    @Override
    public void validate(Certificate certificate) throws ValidatorException {
        if (certificate == null) {
            throw new ValidatorException("Certificate should be not null");
        }
        if (certificate.getName() == null || certificate.getName().isEmpty()) {
            throw new ValidatorException("Certificate name should be not empty");
        }
        if (certificate.getName().length() < MIN_NAME_SIZE) {
            throw new ValidatorException("Certificate name should be at least 3 characters");
        }
        if (certificate.getName().length() > MAX_NAME_SIZE) {
            throw new ValidatorException("Certificate name should be not more than 30 characters");
        }
        if (certificate.getDescription() == null || certificate.getDescription().isEmpty()) {
            throw new ValidatorException("Certificate description should be not empty");
        }
        if (certificate.getDuration() <= 0) {
            throw new ValidatorException("Certificate duration should be more than 0");
        }
        if (certificate.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidatorException("Certificate price should be more than 0 or equals 0");
        }
    }
}
