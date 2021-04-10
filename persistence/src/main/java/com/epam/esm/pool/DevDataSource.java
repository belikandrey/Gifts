package com.epam.esm.pool;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
//@Profile("dev")
public class DevDataSource implements com.epam.esm.pool.DataSource {

    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                        .setType(EmbeddedDatabaseType.H2)
                        .addScript("sql/init_h2.sql")
                        .build();
    }
}
