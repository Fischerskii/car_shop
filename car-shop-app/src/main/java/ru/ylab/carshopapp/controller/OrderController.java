package ru.ylab.carshopapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ylab.carshopapp.dto.OrderDTO;
import ru.ylab.carshopapp.entity.Order;
import ru.ylab.carshopapp.mapper.OrderMapper;
import ru.ylab.carshopapp.service.OrderService;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(OrderDTO orderDTO) {
        try {
            orderService.createOrder(orderMapper.toEntity(orderDTO));
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateOrderStatus(@RequestBody OrderDTO orderDTO) {
        try {
            orderService.changeOrderStatus(orderDTO.getId(), orderDTO.getStatus());
            return ResponseEntity.ok(orderMapper.toEntity(orderDTO));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable UUID orderId) {
        Optional<Order> order = orderService.getOrderById(orderId);
        return order.map(value -> ResponseEntity.ok(orderMapper.toDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
