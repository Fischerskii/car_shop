package ru.ylab.hw.view;

public interface TerminalFactory {
    AuthTerminal createAuthTerminal();

    CarTerminal createCarTerminal();

    OrderTerminal createOrderTerminal();

}
