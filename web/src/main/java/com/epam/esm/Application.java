package com.epam.esm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

/** The type Spring boot application. */
@SpringBootApplication
public class Application {
  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public ResourceBundleMessageSource messageSource() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setDefaultEncoding("UTF-8");
    messageSource.setBasename("messages");
    return messageSource;
  }
}
