package ru.ylab.hw1.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import ru.ylab.hw1.dto.Car;
import ru.ylab.hw1.dto.Request;
import ru.ylab.hw1.dto.User;
import ru.ylab.hw1.service.RequestService;

import static org.assertj.core.api.Assertions.assertThat;

class RequestServiceTest {

    @InjectMocks
    private RequestService requestService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        requestService = new RequestServiceImpl();
    }

    @Test
    void createServiceRequest() {
        User client = new User("client", "password", User.Role.CLIENT);
        Car car = new Car("Toyota", "Camry", 2020, 30000, "New");
        requestService.createServiceRequest(client, car, "Oil change");
        assertThat(requestService.getAllServiceRequests()).hasSize(1);
    }

    @Test
    void changeServiceRequestStatus() {
        User client = new User("client", "password", User.Role.CLIENT);
        Car car = new Car("Toyota", "Camry", 2020, 30000, "New");
        requestService.createServiceRequest(client, car, "Oil change");
        requestService.changeServiceRequestStatus(1, Request.ServiceStatus.COMPLETED);
        assertThat(requestService.getAllServiceRequests().get(0).getStatus()).isEqualTo(Request.ServiceStatus.COMPLETED);
    }
}