package com.epam.esm.config;

import com.epam.esm.dao.impl.CertificateDAO;
import com.epam.esm.dao.impl.TagDAO;
import com.epam.esm.dto.converter.impl.CertificateConverter;
import com.epam.esm.dto.converter.impl.TagConverter;
import com.epam.esm.service.impl.CertificateService;
import com.epam.esm.service.impl.TagService;
import com.epam.esm.validator.impl.CertificateValidator;
import com.epam.esm.validator.impl.TagValidator;
import static org.mockito.Mockito.mock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.epam.esm")
public class TestConfig {

  @Bean
  public TagService tagService() {
    return new TagService(tagValidator(), tagDAO(), tagConverter());
  }

  @Bean
  public TagDAO tagDAO() {
    return mock(TagDAO.class);
  }

  @Bean
  public TagValidator tagValidator() {
    return mock(TagValidator.class);
  }

  @Bean
  public TagConverter tagConverter() {
    return mock(TagConverter.class);
  }

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
