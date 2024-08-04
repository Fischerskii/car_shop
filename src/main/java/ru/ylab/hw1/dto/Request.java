package ru.ylab.hw1.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class Request {
    public enum ServiceStatus {
        PENDING, COMPLETED, CANCELLED
    }

    private static int idCounter = 1;
    @Getter
    private int id;
    private User client;
    private Car car;
    private String description;
    private ServiceStatus status;
    private Date requestDate;

    public Request(User client, Car car, String description) {
        this.id = idCounter++;
        this.client = client;
        this.car = car;
        this.description = description;
        this.status = ServiceStatus.PENDING;
        this.requestDate = new Date();
    }
}
