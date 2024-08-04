package ru.ylab.hw1.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import ru.ylab.hw1.dto.Car;
import ru.ylab.hw1.dto.Order;
import ru.ylab.hw1.dto.User;
import ru.ylab.hw1.service.OrderService;

import static org.assertj.core.api.Assertions.assertThat;

class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderServiceImpl();
    }

    @Test
    void testCreateOrder() {
        User client = new User("client", "password", User.Role.CLIENT);
        Car car = new Car("Toyota", "Camry", 2020, 30000, "New");
        orderService.createOrder(client, car);
        assertThat(orderService.getAllOrders()).hasSize(1);
    }

    @Test
    void testChangeOrderStatus() {
        User client = new User("client", "password", User.Role.CLIENT);
        Car car = new Car("Toyota", "Camry", 2020, 30000, "New");
        orderService.createOrder(client, car);
        orderService.changeOrderStatus(1, Order.OrderStatus.COMPLETED);
        assertThat(orderService.getAllOrders().get(0).getStatus()).isEqualTo(Order.OrderStatus.COMPLETED);
    }
}