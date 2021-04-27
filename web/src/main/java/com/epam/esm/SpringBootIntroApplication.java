package com.epam.esm;

import com.epam.esm.config.PersistenceSpringConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
public class SpringBootIntroApplication {
  public static void main(String[] args) {
    SpringApplication.run(SpringBootIntroApplication.class, args);
  }
}
