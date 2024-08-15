package ru.ylab.hw.config;

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
import java.sql.SQLException;
import java.util.Properties;

/**
 * A utility class responsible for running Liquibase database migrations.
 * <p>
 * The class loads the necessary database configuration from a properties file and executes Liquibase updates.
 */
@Slf4j
public class LiquibaseRunner {
    private final Properties properties;

    /**
     * Initializes a new instance of the {@code LiquibaseRunner} class.
     * <p>
     * Loads the database and Liquibase configuration properties from the specified properties file.
     *
     * @throws RuntimeException if the properties file cannot be loaded.
     */
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

    /**
     * Executes Liquibase update to apply changes to the database schema.
     * <p>
     * Establishes a database connection using the properties loaded in the constructor and runs the Liquibase update.
     *
     * @throws RuntimeException if a database connection or Liquibase operation fails.
     */
    public void runLiquibaseUpdate() {
        String changelogFile = properties.getProperty("liquibase.changelog");

        try (Connection connection = DatabaseConfig.getConnection()) {
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
