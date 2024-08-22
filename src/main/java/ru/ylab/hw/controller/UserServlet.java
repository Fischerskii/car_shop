package ru.ylab.hw.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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
     * <p>
     * This method routes the request to the appropriate handler based on the URI path. It supports
     * login, registration, and logout operations.
     * </p>
     *
     * @param req  the {@link HttpServletRequest} object that contains the request the client made
     *             to the servlet
     * @param resp the {@link HttpServletResponse} object that contains the response the servlet
     *             returns to the client
     * @throws IOException if an input or output error occurs while the servlet is handling the request
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getRequestURI();

        switch (path) {
            case "/users":
                handleLogin(req, resp);
                break;
            case "/register":
                handleRegister(req, resp);
                break;
            case "/logout":
                handleLogout(req, resp);
                break;
            default:
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Invalid endpoint.");
                break;
        }
    }

    /**
     * Handles {@code GET} requests to retrieve user information. If a username is specified in the request URL,
     * the corresponding user information is retrieved. Otherwise, all users are returned.
     * <p>
     * This method either retrieves user details based on the username provided in the path or lists all users
     * if no specific username is provided. It returns the data in JSON format.
     * </p>
     *
     * @param req  the {@link HttpServletRequest} object that contains the request the client made
     *             to the servlet
     * @param resp the {@link HttpServletResponse} object that contains the response the servlet
     *             returns to the client
     * @throws IOException if an input or output error occurs while the servlet is handling the request
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

    /**
     * Handles user login requests.
     * <p>
     * This method reads the login credentials (username and password) from the request body, attempts to authenticate
     * the user, and returns a JWT token if successful. If authentication fails, it returns a 400 Bad Request status.
     * </p>
     *
     * @param req  the HttpServletRequest object that contains the request the client made to the servlet
     * @param resp the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws IOException if an input or output error occurs while the servlet is handling the request
     */
    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserDTO userDTO = objectMapper.readValue(req.getReader(), UserDTO.class);
        try (PrintWriter writer = resp.getWriter()) {
            String token = userService.login(userDTO.getUsername(), userDTO.getPassword());
            resp.setStatus(HttpServletResponse.SC_OK);
            writer.write(token);
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(e.getMessage());
        }
    }

    /**
     * Handles user registration requests.
     * <p>
     * This method reads the registration details (username, password, and role) from the request body, creates
     * a new user, and returns a success message. If registration fails, it returns a 400 Bad Request status.
     * </p>
     *
     * @param req  the HttpServletRequest object that contains the request the client made to the servlet
     * @param resp the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws IOException if an input or output error occurs while the servlet is handling the request
     */
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

    /**
     * Handles user logout requests.
     * <p>
     * This method reads the JWT token from the Authorization header, adds it to the blacklist, and returns
     * a success message. If the token is missing or invalid, it returns a 400 Bad Request status.
     * </p>
     *
     * @param req  the HttpServletRequest object that contains the request the client made to the servlet
     * @param resp the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws IOException if an input or output error occurs while the servlet is handling the request
     */
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
