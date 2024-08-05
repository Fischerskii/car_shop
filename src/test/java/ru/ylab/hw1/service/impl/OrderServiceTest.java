package ru.ylab.hw1.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.ylab.hw1.dto.Car;
import ru.ylab.hw1.dto.Order;
import ru.ylab.hw1.dto.User;
import ru.ylab.hw1.repository.OrderRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrder_ShouldSaveOrder() {
        User client = new User("Pavel", "password", User.Role.CLIENT);
        Car car = new Car("Toyota", "Camry", 2020, 30000, "New");
        Order order = new Order(client, car);

        orderService.createOrder(client, car);

        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void changeOrderStatus_ShouldEditOrderStatusUsingRepository() {
        int orderId = 1;
        Order.OrderStatus status = Order.OrderStatus.COMPLETED;

        orderService.changeOrderStatus(orderId, status);

        verify(orderRepository, times(1)).edit(orderId, status);
    }

    @Test
    void viewOrders_ShouldPrintAllOrders() {
        Order order1 = new Order(new User("Pavel", "password", User.Role.CLIENT),
                new Car("Toyota", "Camry", 2020, 30000, "New"));
        Order order2 = new Order(new User("jane_doe", "password", User.Role.CLIENT),
                new Car("Honda", "Civic", 2019, 25000, "Used"));
        when(orderRepository.findAll()).thenReturn(List.of(order1, order2));

        orderService.viewOrders();

        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void getAllOrders_ShouldReturnAllOrdersUsingRepository() {
        Order order1 = new Order(new User("Pavel", "password", User.Role.CLIENT),
                new Car("Toyota", "Camry", 2020, 30000, "New"));
        Order order2 = new Order(new User("Sid", "password", User.Role.CLIENT),
                new Car("Honda", "Civic", 2019, 25000, "Used"));
        when(orderRepository.findAll()).thenReturn(List.of(order1, order2));

        List<Order> orders = orderService.getAllOrders();

        assertThat(orders).containsExactly(order1, order2);
        verify(orderRepository, times(1)).findAll();
    }
}