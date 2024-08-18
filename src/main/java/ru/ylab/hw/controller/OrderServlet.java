package ru.ylab.hw.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.ylab.hw.dto.OrderDTO;
import ru.ylab.hw.entity.Order;
import ru.ylab.hw.mapper.OrderMapper;
import ru.ylab.hw.repository.impl.OrderRepositoryImpl;
import ru.ylab.hw.service.OrderService;
import ru.ylab.hw.service.impl.OrderServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@WebServlet("/api/orders/*")
public class OrderServlet extends HttpServlet {

    private final OrderService orderService;
    private final ObjectMapper objectMapper;

    public OrderServlet() {
        this.orderService = new OrderServiceImpl(new OrderRepositoryImpl());
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Handles {@code POST} requests to create a new order.
     *
     * @param req  the {@link HttpServletRequest} object that contains the request the client made
     *             to the servlet
     * @param resp the {@link HttpServletResponse} object that contains the response the servlet
     *             returns to the client
     * @throws IOException if the request for the {@code POST} cannot be handled
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            OrderDTO orderDTO = objectMapper.readValue(req.getReader(), OrderDTO.class);

            Order order = OrderMapper.INSTANCE.toEntity(orderDTO);
            orderService.createOrder(order);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write(objectMapper.writeValueAsString(OrderMapper.INSTANCE.toDTO(order)));
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(e.getMessage());
        }
    }

    /**
     * Handles {@code PUT} requests to update an existing order.
     *
     * @param req  the {@link HttpServletRequest} object that contains the request the client made
     *             to the servlet
     * @param resp the {@link HttpServletResponse} object that contains the response the servlet
     *             returns to the client
     * @throws IOException if the request for the {@code PUT} cannot be handled
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UUID orderId = getOrderIdFromPath(req);

        try {
            OrderDTO orderDTO = objectMapper.readValue(req.getInputStream(), OrderDTO.class);
            orderDTO.setId(orderId);

            Order order = OrderMapper.INSTANCE.toEntity(orderDTO);
            orderService.changeOrderStatus(order.getId(), order.getStatus());
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(objectMapper.writeValueAsString(OrderMapper.INSTANCE.toDTO(order)));
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(e.getMessage());
        }
    }

    /**
     * Handles {@code GET} requests to retrieve one or more orders.
     *
     * @param req  the {@link HttpServletRequest} object that contains the request the client made
     *             to the servlet
     * @param resp the {@link HttpServletResponse} object that contains the response the servlet
     *             returns to the client
     * @throws IOException if the request for the {@code GET} cannot be handled
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UUID orderId = getOrderIdFromPath(req);

        if (orderId != null) {
            Optional<Order> order = orderService.getOrderById(orderId);
            if (order.isPresent()) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write(objectMapper.writeValueAsString(OrderMapper.INSTANCE.toDTO(order.get())));
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            List<Order> allOrders = orderService.getAllOrders();
            resp.setStatus(HttpServletResponse.SC_OK);
            List<OrderDTO> orderDTOS = allOrders.stream()
                    .map(OrderMapper.INSTANCE::toDTO)
                    .toList();
            resp.getWriter().write(objectMapper.writeValueAsString(orderDTOS));
        }
    }

    /**
     * Extracts the order ID from the request path.
     *
     * @param req the HTTP request
     * @return the order ID or {@code null} if not present
     */
    private UUID getOrderIdFromPath(HttpServletRequest req) {
        String path = req.getPathInfo();
        if (path == null || path.equals("/")) {
            return null;
        }
        return UUID.fromString(path.substring(1));
    }
}
