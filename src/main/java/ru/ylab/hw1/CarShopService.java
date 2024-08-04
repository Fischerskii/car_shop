package ru.ylab.hw1;


import ru.ylab.hw1.view.AuthTerminalService;
import ru.ylab.hw1.view.CarTerminalService;
import ru.ylab.hw1.view.TerminalService;
import ru.ylab.hw1.service.AuthService;
import ru.ylab.hw1.service.impl.AuthServiceImpl;

public class CarShopService {
    private static final AuthTerminalService authTerminalService = new AuthTerminalService();
    private static final CarTerminalService carTerminalService = new CarTerminalService();

    private static final TerminalService terminalService = new TerminalService(authTerminalService, carTerminalService);

    public static void main(String[] args) {
        terminalService.authenticatePanel();
    }
}
