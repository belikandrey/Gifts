package com.epam.esm.config;

import com.epam.esm.dao.impl.CertificateDAOImpl;
import com.epam.esm.dto.converter.impl.CertificateConverter;
import com.epam.esm.service.impl.CertificateServiceImpl;
import com.epam.esm.service.impl.TagServiceImpl;
import com.epam.esm.validator.impl.CertificateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
@ComponentScan("com.epam.esm")
public class CertificateTestConfig {
/*
  @Bean
  public CertificateServiceImpl certificateService() {
    return new CertificateServiceImpl(
        certificateValidator(), certificateDAO(), tagServiceMock(), certificateConverter());
  }

  @Bean
  public CertificateValidator certificateValidator() {
    return mock(CertificateValidator.class);
  }

  @Bean
  public CertificateDAOImpl certificateDAO() {
    return mock(CertificateDAOImpl.class);
  }

  @Bean
  public TagServiceImpl tagServiceMock() {
    return mock(TagServiceImpl.class);
  }

  @Bean
  public CertificateConverter certificateConverter() {
    return mock(CertificateConverter.class);
  }*/
}
