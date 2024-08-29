package ru.ylab.hw.config;

import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackages = "ru.ylab.hw")
@PropertySource("classpath:application.properties")
@EnableAspectJAutoProxy
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
