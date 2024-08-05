package ru.ylab.hw1.repository;

import ru.ylab.hw1.dto.Request;

import java.util.ArrayList;
import java.util.List;

public class RequestRepository {
    private final List<Request> requests = new ArrayList<>();

    public void save(Request request) {
        requests.add(request);
    }

    public void edit(int id, Request.ServiceStatus status) {
        for (Request request : requests) {
            if (request.getId() == id) {
                request.setStatus(status);
                break;
            }
        }
    }

    public List<Request> findAll() {
        return requests;
    }
}
