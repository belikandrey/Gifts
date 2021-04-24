package com.epam.esm.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "com.epam.esm")
@EntityScan(basePackages = {"com.epam.esm.entity"})
@Import(PersistenceSpringConfig.class)
public class SpringBootIntroApplication {
  public static void main(String[] args) {
    SpringApplication.run(SpringBootIntroApplication.class, args);
  }
}
