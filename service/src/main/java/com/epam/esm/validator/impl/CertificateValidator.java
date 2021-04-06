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
        validateName(certificate.getName());
        validateDescription(certificate.getDescription());
        validateDuration(certificate.getDuration());
        validatePrice(certificate.getPrice());
    }

    private void validateName(String name) throws ValidatorException {
        if(name==null || name.isEmpty()){
            throw new ValidatorException("Certificate name should be not empty");
        }
        if(name.length()<MIN_NAME_SIZE){
            throw new ValidatorException("Certificate name should be at least 3 characters");
        }
        if(name.length()>MAX_NAME_SIZE){
            throw new ValidatorException("Certificate name should be not more than 30 characters");
        }
    }

    private void validateDescription(String description) throws ValidatorException {
        if (description == null || description.isEmpty()) {
            throw new ValidatorException("Certificate description should be not empty");
        }
    }

    private void validateDuration(int duration) throws ValidatorException {
        if (duration <= 0) {
            throw new ValidatorException("Certificate duration should be more than 0");
        }
    }

    private void validatePrice(BigDecimal price) throws ValidatorException {
        if(price==null){
            throw new ValidatorException("Certificate price should be not null");
        }
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidatorException("Certificate price should be more than or equals 0");
        }
    }
}
