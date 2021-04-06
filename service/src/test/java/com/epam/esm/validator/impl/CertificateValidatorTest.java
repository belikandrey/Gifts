package com.epam.esm.validator.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.exception.ValidatorException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CertificateValidatorTest {
    private static CertificateValidator validator;

    private static final String SMALL_CERTIFICATE_NAME = "Ab";
    private static final String BIG_CERTIFICATE_NAME = "Big certificate name. Very big name";
    private static final String NORMAL_CERTIFICATE_NAME = "Apple.com";
    private static final String NORMAL_CERTIFICATE_DESCRIPTION = "Certificate for Iphone X";
    private static final int NORMAL_CERTIFICATE_DURATION = 42;
    private static final BigDecimal NORMAL_CERTIFICATE_PRICE = BigDecimal.TEN;
    private static final int NEGATIVE_CERTIFICATE_DURATION = -42;
    private static final BigDecimal NEGATIVE_CERTIFICATE_PRICE = BigDecimal.valueOf(-42);

    private static final String NULL_CERTIFICATE_EXCEPTION_MESSAGE = "Certificate should be not null";
    private static final String EMPTY_CERTIFICATE_NAME_EXCEPTION_MESSAGE = "Certificate name should be not empty";
    private static final String SMALL_CERTIFICATE_NAME_EXCEPTION_MESSAGE = "Certificate name should be at least 3 characters";
    private static final String BIG_CERTIFICATE_NAME_EXCEPTION_MESSAGE = "Certificate name should be not more than 30 characters";
    private static final String EMPTY_CERTIFICATE_DESCRIPTION_EXCEPTION_MESSAGE = "Certificate description should be not empty";
    private static final String NEGATIVE_CERTIFICATE_DURATION_EXCEPTION_MESSAGE = "Certificate duration should be more than 0";
    private static final String NEGATIVE_CERTIFICATE_PRICE_EXCEPTION_MESSAGE = "Certificate price should be more than or equals 0";

    @BeforeAll
    public static void init(){
        validator = new CertificateValidator();
    }



    @Test
    public void rightCertificateTest(){
        Certificate rightCertificate = new Certificate(BigInteger.ONE, NORMAL_CERTIFICATE_NAME,
                NORMAL_CERTIFICATE_DESCRIPTION, NORMAL_CERTIFICATE_PRICE ,
                NORMAL_CERTIFICATE_DURATION, LocalDateTime.now(), LocalDateTime.now());
        assertDoesNotThrow(()->validator.validate(rightCertificate));
    }

    @Test
    public void nullCertificateTest(){
        final ValidatorException exception = assertThrows(ValidatorException.class, () -> validator.validate(null));
        assertEquals(exception.getMessage(), NULL_CERTIFICATE_EXCEPTION_MESSAGE);
    }

    @Test
    public void emptyCertificateNameTest(){
        Certificate certificate = new Certificate();
        final ValidatorException exception = assertThrows(ValidatorException.class, () -> validator.validate(certificate));
        assertEquals(exception.getMessage(), EMPTY_CERTIFICATE_NAME_EXCEPTION_MESSAGE);
    }

    @Test
    public void smallCertificateNameTest(){
        Certificate certificate = new Certificate();
        certificate.setName(SMALL_CERTIFICATE_NAME);
        final ValidatorException exception = assertThrows(ValidatorException.class, () -> validator.validate(certificate));
        assertEquals(exception.getMessage(), SMALL_CERTIFICATE_NAME_EXCEPTION_MESSAGE);
    }

    @Test
    public void bigCertificateNameTest(){
        Certificate certificate = new Certificate();
        certificate.setName(BIG_CERTIFICATE_NAME);
        final ValidatorException exception = assertThrows(ValidatorException.class, () -> validator.validate(certificate));
        assertEquals(exception.getMessage(), BIG_CERTIFICATE_NAME_EXCEPTION_MESSAGE);
    }

    @Test
    public void emptyCertificateDescriptionTest(){
        Certificate certificate = new Certificate();
        certificate.setName(NORMAL_CERTIFICATE_NAME);
        final ValidatorException exception = assertThrows(ValidatorException.class, () -> validator.validate(certificate));
        assertEquals(exception.getMessage(), EMPTY_CERTIFICATE_DESCRIPTION_EXCEPTION_MESSAGE);
    }

    @Test
    public void negativeCertificateDurationTest(){
        Certificate certificate = new Certificate();
        certificate.setName(NORMAL_CERTIFICATE_NAME);
        certificate.setDescription(NORMAL_CERTIFICATE_DESCRIPTION);
        certificate.setDuration(NEGATIVE_CERTIFICATE_DURATION);
        final ValidatorException exception = assertThrows(ValidatorException.class, () -> validator.validate(certificate));
        assertEquals(exception.getMessage(), NEGATIVE_CERTIFICATE_DURATION_EXCEPTION_MESSAGE);
    }

    @Test
    public void negativeCertificatePriceTest(){
        Certificate certificate = new Certificate();
        certificate.setName(NORMAL_CERTIFICATE_NAME);
        certificate.setDescription(NORMAL_CERTIFICATE_DESCRIPTION);
        certificate.setDuration(NORMAL_CERTIFICATE_DURATION);
        certificate.setPrice(NEGATIVE_CERTIFICATE_PRICE);
        final ValidatorException exception = assertThrows(ValidatorException.class, () -> validator.validate(certificate));
        assertEquals(exception.getMessage(), NEGATIVE_CERTIFICATE_PRICE_EXCEPTION_MESSAGE);
    }

}