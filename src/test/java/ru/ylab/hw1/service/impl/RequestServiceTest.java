package ru.ylab.hw1.service.impl;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.ylab.hw1.dto.Car;
import ru.ylab.hw1.dto.Request;
import ru.ylab.hw1.dto.User;
import ru.ylab.hw1.repository.RequestRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class RequestServiceTest {

    @Mock
    private RequestRepository requestRepository;

    @InjectMocks
    private RequestServiceImpl requestService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createServiceRequest_ShouldSaveRequest() {
        User client = new User("Pavel", "password", User.Role.CLIENT);
        Car car = new Car("Toyota", "Camry", 2020, 30000, "New");
        Request request = new Request(client, car, "Need service");

        requestService.createServiceRequest(client, car, "Need service");

        verify(requestRepository, times(1)).save(any(Request.class));
    }

    @Test
    void changeServiceRequestStatus_ShouldEditRequestStatusUsingRepository() {
        int requestId = 1;
        Request.ServiceStatus status = Request.ServiceStatus.COMPLETED;

        requestService.changeServiceRequestStatus(requestId, status);

        verify(requestRepository, times(1)).edit(requestId, status);
    }

    @Test
    void viewServiceRequests_ShouldPrintAllRequests() {
        Request request1 = new Request(new User("Pavel", "password", User.Role.CLIENT),
                new Car("Toyota", "Camry", 2020, 30000, "New"), "Need service");
        Request request2 = new Request(new User("Sid", "password", User.Role.CLIENT),
                new Car("Honda", "Civic", 2019, 25000, "Used"), "Need another service");
        when(requestRepository.findAll()).thenReturn(List.of(request1, request2));

        requestService.viewServiceRequests();

        verify(requestRepository, times(1)).findAll();
    }

    @Test
    void getAllServiceRequests_ShouldReturnAllRequestsUsingRepository() {
        Request request1 = new Request(new User("Pavel", "password", User.Role.CLIENT),
                new Car("Toyota", "Camry", 2020, 30000, "New"), "Need service");
        Request request2 = new Request(new User("Sid", "password", User.Role.CLIENT),
                new Car("Honda", "Civic", 2019, 25000, "Used"), "Need another service");
        when(requestRepository.findAll()).thenReturn(List.of(request1, request2));

        List<Request> requests = requestService.getAllServiceRequests();

        assertThat(requests).containsExactly(request1, request2);
        verify(requestRepository, times(1)).findAll();
    }
}