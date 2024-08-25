package ru.ylab.hw.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.ylab.hw.entity.Order;
import ru.ylab.hw.enums.OrderStatus;
import ru.ylab.hw.repository.OrderRepository;
import ru.ylab.hw.service.OrderService;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create a new order successfully")
    void createOrder_ShouldCallSaveMethod() {
        Order order = createTestOrder();

        orderService.createOrder(order);

        verify(orderRepository, times(1)).save(order);
    }

    @Test
    @DisplayName("Should change order status successfully")
    void changeOrderStatus_ShouldCallEditMethod() {
        UUID orderId = UUID.randomUUID();
        OrderStatus newStatus = OrderStatus.COMPLETED;

        orderService.changeOrderStatus(orderId, newStatus);

        verify(orderRepository, times(1)).edit(orderId, newStatus);
    }

    @Test
    @DisplayName("Should return order by ID if it exists")
    void getOrderById_ShouldReturnOrder_WhenExists() {
        UUID orderId = UUID.randomUUID();
        Order order = createTestOrder();
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        Optional<Order> result = orderService.getOrderById(orderId);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(orderId);
    }

    @Test
    @DisplayName("Should return empty if order does not exist")
    void getOrderById_ShouldReturnEmpty_WhenNotExists() {
        UUID orderId = UUID.randomUUID();
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        Optional<Order> result = orderService.getOrderById(orderId);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should return all orders from the repository")
    void getAllOrders_ShouldReturnAllOrders() {
        List<Order> orders = Arrays.asList(createTestOrder(), createTestOrder());
        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = orderService.getAllOrders();

        assertThat(result).hasSize(2);
        verify(orderRepository, times(1)).findAll();
    }

    private Order createTestOrder() {
        return Order.builder()
                .id(UUID.randomUUID())
                .username("john_doe")
                .carVinNumber("1HGCM82633A004352")
                .status(OrderStatus.PENDING)
                .orderCreationDate(LocalDateTime.now())
                .build();
    }
}