package com.epam.esm.config;

import com.epam.esm.dao.impl.CertificateDAO;
import com.epam.esm.dto.converter.impl.CertificateConverter;
import com.epam.esm.service.impl.CertificateService;
import com.epam.esm.service.impl.TagService;
import com.epam.esm.validator.impl.CertificateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
@ComponentScan("com.epam.esm")
public class CertificateTestConfig {

  @Bean
  public CertificateService certificateService() {
    return new CertificateService(
        certificateValidator(), certificateDAO(), tagServiceMock(), certificateConverter());
  }

  @Bean
  public CertificateValidator certificateValidator() {
    return mock(CertificateValidator.class);
  }

  @Bean
  public CertificateDAO certificateDAO() {
    return mock(CertificateDAO.class);
  }

  @Bean
  public TagService tagServiceMock() {
    return mock(TagService.class);
  }

  @Bean
  public CertificateConverter certificateConverter() {
    return mock(CertificateConverter.class);
  }
}
