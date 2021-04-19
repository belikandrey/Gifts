package com.epam.esm.config;

import com.epam.esm.entity.Tag;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
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
@ComponentScan("com.epam.esm")
@EnableTransactionManagement
public class PersistenceSpringConfig {

  @Bean
  public PlatformTransactionManager transactionManager(DataSource dataSource){
    return new DataSourceTransactionManager(dataSource);
  }

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
