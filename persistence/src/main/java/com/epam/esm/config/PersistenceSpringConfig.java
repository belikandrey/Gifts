package com.epam.esm.config;

import com.epam.esm.entity.Tag;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "com.epam.esm")
@EnableTransactionManagement
//@EnableJpaRepositories("com.epam.esm.dao1")
public class PersistenceSpringConfig {

  @Bean
  @Profile("dev")
  public DataSource dataSource() {
    return new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .setName("gifts")
            .setCommentPrefix("gifts.")
            .addScript("/sql/init_h2.sql")
            .build();
  }

  @Bean
  public RowMapper<Tag> tagRowMapper(){
    return new BeanPropertyRowMapper<>(Tag.class);
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
