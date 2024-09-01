package ru.ylab.carshopapp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.ylab.carshopapp.BaseTest;
import ru.ylab.carshopapp.dto.OrderDTO;
import ru.ylab.carshopapp.entity.Order;
import ru.ylab.carshopapp.enums.OrderStatus;
import ru.ylab.carshopapp.mapper.OrderMapper;
import ru.ylab.carshopapp.service.OrderService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
public class OrderControllerTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private OrderMapper orderMapper;

    private OrderDTO orderDTO;
    private Order order;
    private UUID orderId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        orderId = UUID.randomUUID();
        orderDTO = OrderDTO.builder()
                .id(orderId)
                .username("john_doe")
                .carVinNumber("VIN123")
                .status(OrderStatus.PENDING)
                .orderCreationDate(LocalDateTime.now())
                .build();

        order = Order.builder()
                .id(orderId)
                .username("john_doe")
                .carVinNumber("VIN123")
                .status(OrderStatus.PENDING)
                .orderCreationDate(LocalDateTime.now())
                .build();
    }

    @Test
    void createOrder_ShouldReturnCreated() throws Exception {
        when(orderMapper.toEntity(any(OrderDTO.class))).thenReturn(order);

        mockMvc.perform(post("/api/orders/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"" + orderId + "\",\"username\":\"john_doe\",\"carVinNumber\":\"VIN123\",\"status\":\"NEW\",\"orderCreationDate\":\"" + orderDTO.getOrderCreationDate() + "\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void updateOrderStatus_ShouldReturnOk() throws Exception {
        when(orderMapper.toEntity(any(OrderDTO.class))).thenReturn(order);

        mockMvc.perform(post("/api/orders/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"" + orderId + "\",\"username\":\"john_doe\",\"carVinNumber\":\"VIN123\",\"status\":\"UPDATED\",\"orderCreationDate\":\"" + orderDTO.getOrderCreationDate() + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderId.toString()))
                .andExpect(jsonPath("$.status").value("UPDATED"));
    }

    @Test
    void getOrder_ShouldReturnOk() throws Exception {
        when(orderService.getOrderById(UUID.randomUUID())).thenReturn(Optional.of(order));
        when(orderMapper.toDTO(any(Order.class))).thenReturn(orderDTO);

        mockMvc.perform(get("/api/orders/{orderId}", orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderId.toString()))
                .andExpect(jsonPath("$.username").value("john_doe"))
                .andExpect(jsonPath("$.carVinNumber").value("VIN123"))
                .andExpect(jsonPath("$.status").value("NEW"));
    }

    @Test
    void getOrder_ShouldReturnNotFound() throws Exception {
        when(orderService.getOrderById(UUID.randomUUID())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/orders/{orderId}", orderId))
                .andExpect(status().isNotFound());
    }
}
