package com.epam.esm.validator.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.validator.Validator;
import org.springframework.stereotype.Service;

@Service
public class CertificateValidator implements Validator<Certificate> {
    @Override
    public void validate(Certificate certificate) throws ValidatorException {
        if (certificate == null) {
            throw new ValidatorException("Certificate should be not null");
        }
        if (certificate.getName() == null || certificate.getName().isEmpty()) {
            throw new ValidatorException("Certificate name should be not empty");
        }
        if (certificate.getName().length() < 3) {
            throw new ValidatorException("Certificate name should be at least 3 characters");
        }
        if (certificate.getName().length() > 30) {
            throw new ValidatorException("Certificate name should be not more than 30 characters");
        }
        if (certificate.getDescription() == null || certificate.getDescription().isEmpty()) {
            throw new ValidatorException("Certificate description should be not empty");
        }
        if (certificate.getDuration() <= 0) {
            throw new ValidatorException("Certificate duration should be more than 0");
        }
        if (certificate.getPrice() < 0) {
            throw new ValidatorException("Certificate price should be more than 0 or equals 0");
        }
    }
}
