package ru.ylab.hw1.service;

import ru.ylab.hw1.dto.Car;
import ru.ylab.hw1.dto.Request;
import ru.ylab.hw1.dto.User;
import ru.ylab.hw1.enums.ServiceStatus;

import java.util.Map;

public interface RequestService {

    void createServiceRequest(User client, Car car, String description);

    void changeServiceRequestStatus(int id, ServiceStatus status);

    void viewServiceRequests();

    Map<Integer, Request> getAllServiceRequests();
}
