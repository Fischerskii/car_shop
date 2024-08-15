package ru.ylab.hw;

import ru.ylab.hw.config.LiquibaseRunner;
import ru.ylab.hw.view.Terminal;

public class CarShopApp {

    public static void main(String[] args) {
        LiquibaseRunner liquibaseRunner = new LiquibaseRunner();
        liquibaseRunner.runLiquibaseUpdate();

        new Terminal().run();
    }
}
