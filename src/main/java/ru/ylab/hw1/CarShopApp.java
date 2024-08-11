package ru.ylab.hw1;

import ru.ylab.hw1.config.LiquibaseRunner;
import ru.ylab.hw1.view.Terminal;

public class CarShopApp {

    public static void main(String[] args) {
        LiquibaseRunner liquibaseRunner = new LiquibaseRunner();
        liquibaseRunner.runLiquibaseUpdate();

        new Terminal().run();
    }
}
