package ru.ylab.carshopapp.annotation;

import org.springframework.context.annotation.Import;
import ru.ylab.carshopapp.config.SpringDocConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(SpringDocConfig.class)
public @interface EnableSpringDoc {
}
