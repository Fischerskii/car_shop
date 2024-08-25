package ru.ylab.hw.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Database configuration class responsible for managing database connections.
 * <p>
 * This class loads the database connection properties from an external file and provides a method to obtain a connection.
 */
@Configuration
@Slf4j
public class DatabaseConfig {

    @Value("${db.url}")
    private String url;

    @Value("${db.username")
    private String username;

    @Value("${db.password}")
    private String password;

    @Bean(initMethod = "init")
    public DatabaseConfig databaseConfig() {
        return new DatabaseConfig();
    }

    public void init() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL Driver not found", e);
        }
    }

    /**
     * Establishes and returns a connection to the database using properties loaded from a configuration file.
     *
     * @return a {@link Connection} object for interacting with the database
     * @throws SQLException if a database access error occurs or the URL is {@code null}
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
