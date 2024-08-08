package ru.ylab.hw1.view;

public interface TerminalFactory {
    AuthTerminal createAuthTerminal();

    CarTerminal createCarTerminal();

    OrderTerminal createOrderTerminal();

    RequestTerminal createRequestTerminal();
}
