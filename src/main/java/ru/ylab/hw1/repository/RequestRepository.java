package ru.ylab.hw1.repository;

import ru.ylab.hw1.dto.Request;
import ru.ylab.hw1.enums.ServiceStatus;

import java.util.Map;

public interface RequestRepository {

    void save(Request request);

    void edit(int id, ServiceStatus status);

    Map<Integer, Request> findAll();
}
