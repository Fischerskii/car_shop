package ru.ylab.hw.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.ylab.hw.config.AppConfig;
import ru.ylab.hw.dto.OrderDTO;
import ru.ylab.hw.entity.Order;
import ru.ylab.hw.enums.OrderStatus;
import ru.ylab.hw.mapper.OrderMapper;
import ru.ylab.hw.service.OrderService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringJUnitConfig
@ContextConfiguration(classes = {AppConfig.class, OrderController.class})
@WebAppConfiguration
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Create order successfully")
    void testCreateOrder_Success() {
        UUID orderId = UUID.randomUUID();
        LocalDateTime orderCreationDate = LocalDateTime.now();

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(orderId);
        orderDTO.setUsername("username");
        orderDTO.setCarVinNumber("1HGCM82633A004352");
        orderDTO.setStatus(OrderStatus.PENDING);
        orderDTO.setOrderCreationDate(orderCreationDate);

        Order order = new Order();
        order.setId(orderId);
        order.setUsername("username");
        order.setCarVinNumber("1HGCM82633A004352");
        order.setStatus(OrderStatus.PENDING);
        order.setOrderCreationDate(orderCreationDate);

        when(orderMapper.toEntity(orderDTO)).thenReturn(order);
        doNothing().when(orderService).createOrder(order);

        ResponseEntity<?> response = orderController.createOrder(orderDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        verify(orderService, times(1)).createOrder(order);
    }

    @Test
    @DisplayName("Update order status successfully")
    void testUpdateOrderStatus_Success() {
        UUID orderId = UUID.randomUUID();

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(orderId);
        orderDTO.setUsername("username");
        orderDTO.setCarVinNumber("1HGCM82633A004352");
        orderDTO.setStatus(OrderStatus.PENDING);
        orderDTO.setOrderCreationDate(LocalDateTime.now());

        doNothing().when(orderService).changeOrderStatus(orderId, OrderStatus.PENDING);

        ResponseEntity<?> response = orderController.updateOrderStatus(orderDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(orderService, times(1)).changeOrderStatus(orderId, OrderStatus.PENDING);
    }

    @Test
    @DisplayName("Get order by ID - order found")
    void testGetOrder_Found() {
        UUID orderId = UUID.randomUUID();
        Order order = new Order();
        order.setId(orderId);
        order.setUsername("username");
        order.setCarVinNumber("1HGCM82633A004352");
        order.setStatus(OrderStatus.PENDING);
        order.setOrderCreationDate(LocalDateTime.now());

        when(orderService.getOrderById(orderId)).thenReturn(Optional.of(order));
        when(orderMapper.toDTO(order)).thenReturn(new OrderDTO());

        ResponseEntity<OrderDTO> response = orderController.getOrder(orderId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(orderService, times(1)).getOrderById(orderId);
    }

    @Test
    @DisplayName("Get order by ID - order not found")
    void testGetOrder_NotFound() {
        UUID orderId = UUID.randomUUID();

        when(orderService.getOrderById(orderId)).thenReturn(Optional.empty());

        ResponseEntity<OrderDTO> response = orderController.getOrder(orderId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(orderService, times(1)).getOrderById(orderId);
    }
}