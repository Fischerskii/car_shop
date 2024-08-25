package ru.ylab.hw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "ru.ylab.hw")
public class AppConfig {

    @Bean
    public DatabaseConfig databaseConfig() {
        return new DatabaseConfig();
    }

    @Bean(initMethod = "runLiquibaseUpdate")
    public LiquibaseRunner liquibaseRunner() {
        return new LiquibaseRunner();
    }
}
