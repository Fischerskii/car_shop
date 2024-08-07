package ru.ylab.hw1.repository.impl;

import ru.ylab.hw1.dto.Request;
import ru.ylab.hw1.enums.ServiceStatus;
import ru.ylab.hw1.repository.RequestRepository;

import java.util.HashMap;
import java.util.Map;

public class RequestRepositoryImpl implements RequestRepository {
    private final Map<Integer, Request> requests = new HashMap<>();
    private static int idCounter = 1;

    public void save(Request request) {
        requests.put(idCounter, request);
        idCounter++;
    }

    public void edit(int id, ServiceStatus status) {
        Request request = requests.get(id);
        request.setStatus(status);
        requests.replace(id, request);
    }

    public Map<Integer, Request> findAll() {
        return new HashMap<>(requests);
    }
}
