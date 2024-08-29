package ru.ylab.hw;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.ylab.hw.config.AppConfig;

public class CarShopApp {

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        System.out.println("CarShopApp is running...");
    }
}
