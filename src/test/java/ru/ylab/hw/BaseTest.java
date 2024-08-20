package ru.ylab.hw;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.junit.jupiter.api.BeforeAll;

import java.util.Properties;

@Testcontainers
@ExtendWith(MockitoExtension.class)
public abstract class BaseTest {

    private static final Properties properties = new Properties();

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>(properties.getProperty("db.docker-image-name"))
                    .withDatabaseName(properties.getProperty("db.name"))
                    .withUsername(properties.getProperty("db.username"))
                    .withPassword(properties.getProperty("db.password"));

    @BeforeAll
    public static void setup() {
        System.setProperty(properties.getProperty("db.url"), postgreSQLContainer.getJdbcUrl());
        System.setProperty(properties.getProperty("db.username"), postgreSQLContainer.getUsername());
        System.setProperty(properties.getProperty("db.password"), postgreSQLContainer.getPassword());
    }
}
