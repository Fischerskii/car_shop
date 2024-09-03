package ru.ylab.carshopapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import ru.ylab.carshopapp.annotation.EnableSpringDoc;

@SpringBootApplication
@EnableSpringDoc
@ComponentScan(basePackages = {"ru.ylab.carshopapp", "ru.ylab.auditlogging", "ru.ylab.common"})
public class CarShopApp {

    public static void main(String[] args) {
        SpringApplication.run(CarShopApp.class, args);
    }
}
