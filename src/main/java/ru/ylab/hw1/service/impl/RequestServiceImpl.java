package ru.ylab.hw1.service.impl;

import ru.ylab.hw1.dto.Car;
import ru.ylab.hw1.dto.Request;
import ru.ylab.hw1.dto.User;
import ru.ylab.hw1.service.RequestService;

import java.util.ArrayList;
import java.util.List;

public class RequestServiceImpl implements RequestService {
    private final List<Request> requests = new ArrayList<>();

    public void createServiceRequest(User client, Car car, String description) {
        requests.add(new Request(client, car, description));
    }

    public void changeServiceRequestStatus(int id, Request.ServiceStatus status) {
        for (Request request : requests) {
            if (request.getId() == id) {
                request.setStatus(status);
                break;
            }
        }
    }

    public void viewServiceRequests() {
        requests.forEach(System.out::println);
    }

    public List<Request> getAllServiceRequests() {
        return requests;
    }
}
