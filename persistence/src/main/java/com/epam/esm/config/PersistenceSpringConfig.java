package com.epam.esm.config;

import com.epam.esm.pool.DataSource;
import com.epam.esm.pool.ProdDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.xml.crypto.Data;

@Configuration
@ComponentScan("com.epam.esm")
public class PersistenceSpringConfig {

  //@Autowired
  //private static DataSource dataSource;

  @Bean
  public static JdbcTemplate getTemplate() {
    return new JdbcTemplate(ProdDataSource.getDataSource());
  }


}
