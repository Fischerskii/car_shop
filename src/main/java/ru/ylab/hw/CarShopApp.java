package ru.ylab.hw;

import ru.ylab.hw.config.LiquibaseRunner;

public class CarShopApp {

    public static void main(String[] args) {
        LiquibaseRunner liquibaseRunner = new LiquibaseRunner();
        liquibaseRunner.runLiquibaseUpdate();

        System.out.println("CarShopApp is running...");
    }
}
