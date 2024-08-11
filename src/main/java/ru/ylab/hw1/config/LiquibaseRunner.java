package ru.ylab.hw1.config;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
public class LiquibaseRunner {
    private final Properties properties;

    public LiquibaseRunner() {
        properties = new Properties();
        String propertiesFilePath = "src/main/resources/application.properties";
        try (FileInputStream input = new FileInputStream(propertiesFilePath)) {
            properties.load(input);
        } catch (IOException e) {
            log.error("Failed to load properties file from path: {}", propertiesFilePath, e);
            throw new RuntimeException("Failed to load properties file", e);
        }
    }

    public void runLiquibaseUpdate() {
        String url = properties.getProperty("db.url");
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");
        String changelogFile = properties.getProperty("liquibase.changelog");

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase(changelogFile, new ClassLoaderResourceAccessor(), database);
            liquibase.update("");
        } catch (SQLException e) {
            log.error("Database connection error", e);
            throw new RuntimeException("Failed to connect to the database", e);
        } catch (LiquibaseException e) {
            log.error("Liquibase update error", e);
            throw new RuntimeException("Liquibase update failed", e);
        }
    }
}
