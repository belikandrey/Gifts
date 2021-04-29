package com.epam.esm.config;

import com.epam.esm.SpringBootIntroApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/** The type Servlet initializer. */
public class ServletInitializer extends SpringBootServletInitializer {

  /**
   * Configure spring application builder.
   *
   * @param application the application
   * @return the spring application builder
   */
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(SpringBootIntroApplication.class);
  }
}
