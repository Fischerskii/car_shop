package ru.ylab.hw1.service.impl;

import ru.ylab.hw1.dto.Car;
import ru.ylab.hw1.dto.Request;
import ru.ylab.hw1.dto.User;
import ru.ylab.hw1.enums.ServiceStatus;
import ru.ylab.hw1.repository.RequestRepository;
import ru.ylab.hw1.service.RequestService;

import java.util.Map;

public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;

    public RequestServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public void createServiceRequest(User client, Car car, String description) {
        requestRepository.save(new Request(client, car, description));
    }

    public void changeServiceRequestStatus(int id, ServiceStatus status) {
        requestRepository.edit(id, status);
    }

    public void viewServiceRequests() {
        getAllServiceRequests().values().forEach(System.out::println);
    }

    public Map<Integer, Request> getAllServiceRequests() {
        return requestRepository.findAll();
    }
}
