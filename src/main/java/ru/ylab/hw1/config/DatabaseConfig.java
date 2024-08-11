package ru.ylab.hw1.config;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
public class DatabaseConfig {

    private static final Properties properties = new Properties();

    static {
        String propertiesFilePath = "src/main/resources/application.properties";
        try (FileInputStream input = new FileInputStream(propertiesFilePath)) {
            properties.load(input);
        } catch (IOException e) {
            log.error("Failed to load properties file from path: {}", propertiesFilePath, e);
            throw new RuntimeException("Failed to load properties file", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");

        return DriverManager.getConnection(url, user, password);
    }
}
