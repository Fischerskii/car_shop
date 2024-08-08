package ru.ylab.hw1.dto;

import lombok.Data;

import java.util.Date;
import ru.ylab.hw1.enums.ServiceStatus;

@Data
public class Request {
    private int id;
    private User client;
    private Car car;
    private String description;
    private ServiceStatus status;
    private Date requestDate;

    public Request(User client, Car car, String description) {
        this.client = client;
        this.car = car;
        this.description = description;
        this.status = ServiceStatus.PENDING;
        this.requestDate = new Date();
    }
}
