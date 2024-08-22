package ru.ylab.hw.config;

import lombok.extern.slf4j.Slf4j;

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
@Slf4j
public class DatabaseConfig {

    private static final Properties properties = new Properties();

    //Static block to load properties from the file at class initialization
    static {
        try (InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new FileNotFoundException("Properties file not found in classpath: application.properties");
            }
            properties.load(input);
        } catch (IOException e) {
            log.error("Failed to load properties file", e);
            throw new RuntimeException("Failed to load properties file", e);
        }

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
    public static Connection getConnection() throws SQLException {
        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");

        return DriverManager.getConnection(url, user, password);
    }
}
