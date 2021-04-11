package com.epam.esm.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.epam.esm")
public class PersistenceSpringConfig {

  @Bean
  public JdbcTemplate getTemplate(DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

  @Bean
  @Profile("dev")
  public DataSource dataSource() {
    return new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .addScript("sql/init_h2.sql")
            .build();
  }

  @Bean
  @Profile("prod")
  public DataSource hikariDataSource(){
    return new HikariDataSource(hikariConfig());
  }

  @Bean
  public HikariConfig hikariConfig(){
    return new HikariConfig("/db.properties");
  }
}
