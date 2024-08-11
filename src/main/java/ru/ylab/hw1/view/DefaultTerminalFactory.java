package ru.ylab.hw1.view;

import ru.ylab.hw1.service.LoggerService;

public class DefaultTerminalFactory implements TerminalFactory {

    private final Terminal terminal;
    private final LoggerService loggerService;

    public DefaultTerminalFactory(Terminal terminal, LoggerService loggerService) {
        this.terminal = terminal;
        this.loggerService = loggerService;
    }

    @Override
    public AuthTerminal createAuthTerminal() {
        return new AuthTerminal(terminal, loggerService);
    }

    @Override
    public CarTerminal createCarTerminal() {
        return new CarTerminal(terminal, loggerService);
    }

    @Override
    public OrderTerminal createOrderTerminal() {
        return new OrderTerminal(terminal, loggerService);
    }
}
