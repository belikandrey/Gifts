package com.epam.esm.config;

import com.epam.esm.dao.impl.CertificateDAOImpl;
import com.epam.esm.dao.impl.TagDAOImpl;
import com.epam.esm.dto.converter.impl.CertificateConverter;
import com.epam.esm.dto.converter.impl.TagConverter;
import com.epam.esm.service.impl.CertificateServiceImpl;
import com.epam.esm.service.impl.TagServiceImpl;
import com.epam.esm.validator.impl.CertificateValidator;
import com.epam.esm.validator.impl.TagValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class TestConfig {
/*

  @Bean
  public TagServiceImpl tagService() {
    return new TagServiceImpl(tagValidator(), tagDAO(), tagConverter());
  }
*/

  @Bean
  public TagDAOImpl tagDAO() {
    return mock(TagDAOImpl.class);
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
  public CertificateServiceImpl certificateService() {
    return new CertificateServiceImpl(
        certificateValidator(), certificateDAO(), certificateConverter(), tagServiceMock() ,null, null);
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
  }
}
