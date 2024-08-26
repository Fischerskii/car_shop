package ru.ylab.hw;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Testcontainers
@ExtendWith(MockitoExtension.class)
public abstract class BaseTest {

    private static final Properties properties = new Properties();

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer;

    @BeforeAll
    public static void setup() {
        try (InputStream input = new FileInputStream("src/main/resources/application.yml")) {
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        postgreSQLContainer = new PostgreSQLContainer<>("postgres:15-alpine")
                .withDatabaseName(properties.getProperty("db.name"))
                .withUsername(properties.getProperty("db.username"))
                .withPassword(properties.getProperty("db.password"))
                .withEnv("POSTGRES_PASSWORD", "pass");

        postgreSQLContainer.start();

        System.setProperty(properties.getProperty("db.url"), postgreSQLContainer.getJdbcUrl());
        System.setProperty(properties.getProperty("db.username"), postgreSQLContainer.getUsername());
        System.setProperty(properties.getProperty("db.password"), postgreSQLContainer.getPassword());
    }
}
