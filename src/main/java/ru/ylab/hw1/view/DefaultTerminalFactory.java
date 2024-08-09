package ru.ylab.hw1.view;

public class DefaultTerminalFactory implements TerminalFactory {

    private final Terminal terminal;

    public DefaultTerminalFactory(Terminal terminal) {
        this.terminal = terminal;
    }

    @Override
    public AuthTerminal createAuthTerminal() {
        return new AuthTerminal(terminal);
    }

    @Override
    public CarTerminal createCarTerminal() {
        return new CarTerminal(terminal);
    }

    @Override
    public OrderTerminal createOrderTerminal() {
        return new OrderTerminal(terminal);
    }

    @Override
    public RequestTerminal createRequestTerminal() {
        return new RequestTerminal(terminal);
    }
}
