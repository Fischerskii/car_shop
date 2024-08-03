package ru.ylab.hw1;


import ru.ylab.hw1.controller.AuthTerminalService;
import ru.ylab.hw1.controller.CarTerminalService;
import ru.ylab.hw1.controller.TerminalService;
import ru.ylab.hw1.service.AuthService;
import ru.ylab.hw1.service.impl.AuthServiceImpl;

public class CarShopService {
    private static final AuthTerminalService authTerminalService = new AuthTerminalService();
    private static final CarTerminalService carTerminalService = new CarTerminalService();
    private static final AuthService authService = new AuthServiceImpl();


    private static final TerminalService terminalService = new TerminalService(authTerminalService, carTerminalService, authService);

    public static void main(String[] args) {
        terminalService.authenticate();
    }
}
