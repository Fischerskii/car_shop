package ru.ylab.carshopapp.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ylab.carshopapp.BaseTest;
import ru.ylab.carshopapp.entity.Order;
import ru.ylab.carshopapp.enums.OrderStatus;
import ru.ylab.carshopapp.repository.impl.OrderRepositoryImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderRepositoryTest extends BaseTest {

    @Autowired
    private OrderRepositoryImpl orderRepository;

    @BeforeEach
    public void setup() {
        // Очистка таблицы перед каждым тестом
        orderRepository.findAll().forEach(order -> orderRepository.edit(order.getId(), OrderStatus.CANCELLED));
    }

    @Test
    public void testSave() {
        Order order = Order.builder()
                .id(UUID.randomUUID())
                .username("user1")
                .carVinNumber("WVGZZZCAZJC552497")
                .status(OrderStatus.PENDING)
                .orderCreationDate(LocalDateTime.now())
                .build();

        orderRepository.save(order);

        Optional<Order> foundOrder = orderRepository.findById(order.getId());
        assertThat(foundOrder).isPresent();
        assertThat(foundOrder.get().getUsername()).isEqualTo("user1");
    }

    @Test
    public void testEdit() {
        Order order = Order.builder()
                .id(UUID.randomUUID())
                .username("user2")
                .carVinNumber("JHMCM56557C404453")
                .status(OrderStatus.PENDING)
                .orderCreationDate(LocalDateTime.now())
                .build();

        orderRepository.save(order);
        orderRepository.edit(order.getId(), OrderStatus.COMPLETED);

        Optional<Order> updatedOrder = orderRepository.findById(order.getId());
        assertThat(updatedOrder).isPresent();
        assertThat(updatedOrder.get().getStatus()).isEqualTo(OrderStatus.COMPLETED);
    }

    @Test
    public void testFindAll() {
        List<Order> orders = orderRepository.findAll();
        assertThat(orders).isNotNull();
    }

    @Test
    public void testFindById() {
        Order order = Order.builder()
                .id(UUID.randomUUID())
                .username("user3")
                .carVinNumber("AUMCM56557C404453")
                .status(OrderStatus.PENDING)
                .orderCreationDate(LocalDateTime.now())
                .build();

        orderRepository.save(order);

        Optional<Order> foundOrder = orderRepository.findById(order.getId());
        assertThat(foundOrder).isPresent();
    }

    @Test
    public void testFindById_NotFound() {
        Optional<Order> order = orderRepository.findById(UUID.randomUUID());
        assertThat(order).isNotPresent();
    }
}