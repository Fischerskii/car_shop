package ru.ylab.auditlogging.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ru.ylab.auditlogging.aspect.AuditAspect;
import ru.ylab.auditlogging.repository.LoggerRepository;
import ru.ylab.auditlogging.service.LoggerService;
import ru.ylab.auditlogging.service.impl.LoggerServiceImpl;

@Configuration
public class AuditLoggingAutoConfiguration {

    @Bean
    public AuditAspect auditAspect(LoggerService loggerService) {
        return new AuditAspect(loggerService);
    }

    @Bean
    @Primary
    public LoggerService loggerService(LoggerRepository loggerRepository) {
        return new LoggerServiceImpl(loggerRepository);
    }
}