package ru.ylab.auditlogging.annotation;

import ru.ylab.common.enums.ActionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Audit {
    ActionType actionType() default ActionType.USER_ACTIONS;
}
