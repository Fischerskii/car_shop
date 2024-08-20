package ru.ylab.hw.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.ylab.hw.BaseTest;
import ru.ylab.hw.entity.Order;
import ru.ylab.hw.enums.OrderStatus;
import ru.ylab.hw.repository.impl.OrderRepositoryImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderRepositoryTest extends BaseTest {
    private Connection connection;
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection(
                postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getPassword()
        );
        connection.setAutoCommit(false);
        orderRepository = new OrderRepositoryImpl();
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.rollback();
        connection.close();
    }

    @Test
    @DisplayName("Should save order and persist data")
    void save_ShouldPersistOrder() {
        Order order = createTestOrder();

        orderRepository.save(order);

        Optional<Order> foundOrder = orderRepository.findById(order.getId());

        assertThat(foundOrder).isPresent();
        assertThat(foundOrder.get().getId()).isEqualTo(order.getId());
    }

    @Test
    @DisplayName("Should update order status successfully")
    void edit_ShouldUpdateOrderStatus() {
        Order order = createTestOrder();
        orderRepository.save(order);

        OrderStatus newStatus = OrderStatus.COMPLETED;
        orderRepository.edit(order.getId(), newStatus);

        Optional<Order> updatedOrder = orderRepository.findById(order.getId());

        assertThat(updatedOrder).isPresent();
        assertThat(updatedOrder.get().getStatus()).isEqualTo(newStatus);
    }

    @Test
    @DisplayName("Should find order by ID if it exists")
    void findById_ShouldReturnOrder_WhenOrderExists() {
        Order order = createTestOrder();
        orderRepository.save(order);

        Optional<Order> foundOrder = orderRepository.findById(order.getId());

        assertThat(foundOrder).isPresent();
        assertThat(foundOrder.get().getId()).isEqualTo(order.getId());
    }

    @Test
    @DisplayName("Should return all orders from the database")
    void findAll_ShouldReturnAllPersistedOrders() {
        Order order1 = createTestOrder();
        Order order2 = createTestOrder();
        orderRepository.save(order1);
        orderRepository.save(order2);

        List<Order> orders = orderRepository.findAll();

        assertThat(orders).hasSize(2);
        assertThat(orders.get(0).getId()).isEqualTo(order1.getId());
        assertThat(orders.get(1).getId()).isEqualTo(order2.getId());
    }

    private Order createTestOrder() {
        return Order.builder()
                .id(UUID.randomUUID())
                .userName("john_doe")
                .carVinNumber("1HGCM82633A004352")
                .status(OrderStatus.PENDING)
                .orderCreationDate(LocalDateTime.now())
                .build();
    }
}
