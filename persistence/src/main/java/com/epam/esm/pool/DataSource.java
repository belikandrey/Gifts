package com.epam.esm.pool;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Connection pool
 * @see com.zaxxer.hikari.HikariDataSource
 *
 */
public class DataSource {
    private static HikariConfig config = new HikariConfig("/db.properties");
    private static HikariDataSource ds = new HikariDataSource(config);

    private DataSource() {
    }

    /**
     * Get {@link com.zaxxer.hikari.HikariDataSource} method
     * @return {@link com.zaxxer.hikari.HikariDataSource} with the established params
     */
    public static HikariDataSource getDataSource() {
        return ds;
    }
}
