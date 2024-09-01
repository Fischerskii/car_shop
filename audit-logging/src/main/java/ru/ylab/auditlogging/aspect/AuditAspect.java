package ru.ylab.auditlogging.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import ru.ylab.auditlogging.annotation.Audit;
import ru.ylab.common.enums.ActionType;
import ru.ylab.auditlogging.service.LoggerService;

import java.util.Arrays;

/**
 * This aspect is designed to intercept methods annotated with {@link Audit} and those within classes annotated
 *  * with {@link Audit}, and it performs auditing actions such as logging execution time and recording user actions.
 */
@Aspect
@Component
@Slf4j
public class AuditAspect {

    private final LoggerService loggerService;

    public AuditAspect(LoggerService loggerService) {
        this.loggerService = loggerService;
    }

    /**
     * Pointcut that matches all methods within classes annotated with {@link Audit}
     * or methods annotated with {@link Audit}.
     */
    @Around("execution(* *(..)) && (@within(ru.ylab.auditlogging.annotation.Audit) || @annotation(ru.ylab.auditlogging.annotation.Audit))")
    public Object logAndAudit(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.nanoTime();

        Object result = joinPoint.proceed();

        long executionTime = System.nanoTime() - start;
        log.info("{} executed in {} ms", joinPoint.getSignature(), executionTime / 1_000_000);

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Audit audit = signature.getMethod().getAnnotation(Audit.class);

        if (audit == null) {
            audit = joinPoint.getTarget().getClass().getAnnotation(Audit.class);
        }

        if (audit != null) {
            ActionType actionType = audit.actionType();
            Object[] args = joinPoint.getArgs();
            loggerService.logAction("system", actionType,
                    "Action performed with args: " + Arrays.toString(args));
        }

        return result;
    }
}

