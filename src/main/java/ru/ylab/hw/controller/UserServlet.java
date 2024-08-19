package ru.ylab.hw.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.ylab.hw.dto.UserDTO;
import ru.ylab.hw.entity.User;
import ru.ylab.hw.repository.impl.UserRepositoryImpl;
import ru.ylab.hw.sequrity.BlacklistService;
import ru.ylab.hw.service.UserService;
import ru.ylab.hw.service.impl.UserServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * The {@code UserServlet} class is a servlet responsible for handling HTTP requests related to user management.
 * It supports operations such as user registration, user login, and retrieving user information.
 *
 */
@WebServlet("/api/users/*")
public class UserServlet extends HttpServlet {
    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final BlacklistService blacklistService;

    public UserServlet() {
        this.userService = new UserServiceImpl(new UserRepositoryImpl());
        this.objectMapper = new ObjectMapper();
        this.blacklistService = new BlacklistService();
    }

    /**
     * Handles {@code POST} requests to either register, login, or logout a user.
     *
     * @param req  the {@link HttpServletRequest} object that contains the request the client made
     *             to the servlet
     * @param resp the {@link HttpServletResponse} object that contains the response the servlet
     *             returns to the client
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getRequestURI().endsWith("/login")) {
            handleLogin(req, resp);
        } else if (req.getRequestURI().endsWith("/register")) {
            handleRegister(req, resp);
        } else if (req.getRequestURI().endsWith("/logout")) {
            handleLogout(req, resp);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid endpoint.");
        }
    }

    /**
     * Handles {@code GET} requests to retrieve user information. If a username is specified in the request URL,
     * the corresponding user information is retrieved. Otherwise, all users are returned.
     *
     * @param req  the {@link HttpServletRequest} object that contains the request the client made
     *             to the servlet
     * @param resp the {@link HttpServletResponse} object that contains the response the servlet
     *             returns to the client
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String username = getUsernameFromPath(req);
            if (username != null) {
                try {
                    User user = userService.getUserByUsername(username);
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.getWriter().write(objectMapper.writeValueAsString(user));
                } catch (NoSuchElementException e) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("User not found: " + username);
                }
            } else {
                List<User> users = userService.viewUsers();
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write(objectMapper.writeValueAsString(users));
            }
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error processing request. Please try again later.");
        }
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserDTO userDTO = objectMapper.readValue(req.getInputStream(), UserDTO.class);
        try (PrintWriter writer = resp.getWriter()) {
            String token = userService.login(userDTO.getUsername(), userDTO.getPassword());
            resp.setStatus(HttpServletResponse.SC_OK);
            writer.write(token);
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(e.getMessage());
        }
    }

    private void handleRegister(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserDTO userDTO = objectMapper.readValue(req.getInputStream(), UserDTO.class);
        try (PrintWriter writer = resp.getWriter()) {
            userService.register(userDTO.getUsername(), userDTO.getPassword(), userDTO.getRole());
            resp.setStatus(HttpServletResponse.SC_CREATED);
            writer.write("User registered successfully.");
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(e.getMessage());
        }
    }

    private void handleLogout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String authHeader = req.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            blacklistService.blacklistToken(token);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("Logged out successfully.");
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid token.");
        }
    }

    /**
     * Extracts the username from the request path.
     *
     * @param req the HTTP request
     * @return the username or {@code null} if not present
     */
    private String getUsernameFromPath(HttpServletRequest req) {
        String path = req.getPathInfo();

        if (path == null || path.equals("/")) {
            return null;
        }

        return path.substring(1);
    }
}
