package ru.ylab.hw.config;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * A utility class responsible for running Liquibase database migrations.
 * <p>
 * The class loads the necessary database configuration from a properties file and executes Liquibase updates.
 */
@Component
@Slf4j
public class LiquibaseRunner {

    @Value("${liquibase.changelog}")
    private String changelogFile;

    /**
     * Executes Liquibase update to apply changes to the database schema.
     * <p>
     * Establishes a database connection using the properties loaded in the constructor and runs the Liquibase update.
     *
     * @throws RuntimeException if a database connection or Liquibase operation fails.
     */
    public void runLiquibaseUpdate() {
        try (Connection connection = new DatabaseConfig().getConnection()) {
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
