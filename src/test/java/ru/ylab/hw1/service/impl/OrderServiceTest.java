package ru.ylab.hw1.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.ylab.hw1.dto.Car;
import ru.ylab.hw1.dto.Order;
import ru.ylab.hw1.dto.User;
import ru.ylab.hw1.enums.OrderStatus;
import ru.ylab.hw1.enums.Role;
import ru.ylab.hw1.repository.impl.OrderRepositoryImpl;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepositoryImpl orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Проверка создания заказов")
    void createOrder_ShouldSaveOrder() {
        User client = new User("Pavel", "password", Role.CLIENT);
        Car car = new Car("Toyota", "Camry", 2020, 30000, "New");

        orderService.createOrder(client, car);

        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    @DisplayName("Проврка работоспособности функционала редактирования заказа")
    void changeOrderStatus_ShouldEditOrderStatusUsingRepository() {
        int orderId = 1;
        OrderStatus status = OrderStatus.COMPLETED;

        orderService.changeOrderStatus(orderId, status);

        verify(orderRepository, times(1)).edit(orderId, status);
    }

    @Test
    @DisplayName("Проверка выдачи всех заказов")
    void getAllOrders_ShouldReturnAllOrdersUsingRepository() {
        Order order1 = new Order(new User("Pavel", "password", Role.CLIENT),
                new Car("Toyota", "Camry", 2020, 30000, "New"));
        Order order2 = new Order(new User("Sid", "password", Role.CLIENT),
                new Car("Honda", "Civic", 2019, 25000, "Used"));
        when(orderRepository.findAll()).thenReturn(Map.of(1, order1, 2, order2));

        Map<Integer, Order> orders = orderService.getAllOrders();

        assertThat(orders).containsExactlyInAnyOrderEntriesOf(Map.of(1, order1, 2, order2));
        verify(orderRepository, times(1)).findAll();
    }
}