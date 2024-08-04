package ru.ylab.hw1.service;

import ru.ylab.hw1.dto.Car;
import ru.ylab.hw1.dto.Request;
import ru.ylab.hw1.dto.User;

import java.util.List;

public interface RequestService {

    void createServiceRequest(User client, Car car, String description);

    void changeServiceRequestStatus(int id, Request.ServiceStatus status);

    void viewServiceRequests();

    List<Request> getAllServiceRequests();
}
