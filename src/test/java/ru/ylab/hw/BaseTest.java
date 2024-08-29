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
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("db.name")
            .withUsername("db.username")
            .withPassword("db.password");

    @BeforeAll
    public static void setup() {
        try (InputStream input = new FileInputStream("src/main/resources/application.properties")) {
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        postgreSQLContainer.start();

        System.setProperty("db.url", postgreSQLContainer.getJdbcUrl());
        System.setProperty("db.username", postgreSQLContainer.getUsername());
        System.setProperty("db.password", postgreSQLContainer.getPassword());
    }
}
