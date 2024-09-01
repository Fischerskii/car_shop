package ru.ylab.carshopapp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.ylab.carshopapp.annotation.EnableSpringDoc;

@SpringBootApplication
@EnableSpringDoc
public class CarShopApp {

    public static void main(String[] args) {
        SpringApplication.run(CarShopApp.class, args);
    }
}
