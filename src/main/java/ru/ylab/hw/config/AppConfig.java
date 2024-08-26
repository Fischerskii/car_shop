package ru.ylab.hw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = "ru.ylab.hw")
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Bean(initMethod = "init")
    public DatabaseConfig databaseConfig() {
        return new DatabaseConfig();
    }

    @Bean(initMethod = "runLiquibaseUpdate")
    public LiquibaseRunner liquibaseRunner() {
        return new LiquibaseRunner(databaseConfig());
    }
}
