//package ru.ylab.hw.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import ru.ylab.hw.entity.User;
//import ru.ylab.hw.enums.Role;
//import ru.ylab.hw.sequrity.BlacklistService;
//import ru.ylab.hw.service.UserService;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.List;
//import java.util.NoSuchElementException;
//
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.*;
//
//public class UserServletTest {
//
//    @Mock
//    private UserService userService;
//
//    @Mock
//    private BlacklistService blacklistService;
//
//    @Mock
//    private HttpServletRequest request;
//
//    @Mock
//    private HttpServletResponse response;
//
//    @Mock
//    private PrintWriter writer;
//
//    @InjectMocks
//    private UserServlet userServlet;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    private void setupRequestAndResponse(String requestUri, String inputData) throws IOException {
//        when(request.getRequestURI()).thenReturn(requestUri);
//        when(request.getInputStream()).thenReturn(new ByteArrayInputStream(inputData.getBytes()));
//        when(response.getWriter()).thenReturn(writer);
//    }
//
//    @Test
//    public void testHandleLoginSuccess() throws Exception {
//        setupRequestAndResponse("/api/users/login", "{\"username\":\"testUser\",\"password\":\"testPass\"}");
//        when(userService.login(anyString(), anyString())).thenReturn("fake-jwt-token");
//
//        userServlet.doPost(request, response);
//
//        verify(response).setStatus(HttpServletResponse.SC_OK);
//        verify(writer).write("fake-jwt-token");
//    }
//
//    @Test
//    public void testHandleLoginFailure() throws Exception {
//        setupRequestAndResponse("/api/users/login", "{\"username\":\"testUser\",\"password\":\"wrongPass\"}");
//        when(userService.login(anyString(), anyString())).thenThrow(new IllegalArgumentException("Invalid username or password"));
//
//        userServlet.doPost(request, response);
//
//        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
//        verify(writer).write("Invalid username or password");
//    }
//
//    @Test
//    public void testHandleRegisterSuccess() throws Exception {
//        setupRequestAndResponse("/api/users/register", "{\"username\":\"testUser\",\"password\":\"testPass\",\"role\":\"USER\"}");
//
//        userServlet.doPost(request, response);
//
//        verify(response).setStatus(HttpServletResponse.SC_CREATED);
//        verify(writer).write("User registered successfully.");
//    }
//
//    @Test
//    public void testHandleRegisterFailure() throws Exception {
//        setupRequestAndResponse("/api/users/register", "{\"username\":\"testUser\",\"password\":\"\"}");
//        when(userService.register(anyString(), anyString(), Role.CLIENT)).thenThrow(new IllegalArgumentException("Password cannot be empty"));
//
//        userServlet.doPost(request, response);
//
//        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
//        verify(writer).write("Password cannot be empty");
//    }
//
//    @Test
//    public void testHandleLogoutSuccess() throws Exception {
//        setupRequestAndResponse("/api/users/logout", "");
//        when(request.getHeader("Authorization")).thenReturn("Bearer valid-token");
//
//        userServlet.doPost(request, response);
//
//        verify(blacklistService).blacklistToken("valid-token");
//        verify(response).setStatus(HttpServletResponse.SC_OK);
//        verify(writer).write("Logged out successfully.");
//    }
//
//    @Test
//    public void testHandleLogoutFailure() throws Exception {
//        setupRequestAndResponse("/api/users/logout", "");
//        when(request.getHeader("Authorization")).thenReturn(null);
//
//        userServlet.doPost(request, response);
//
//        verify(blacklistService, never()).blacklistToken(anyString());
//        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
//        verify(writer).write("Invalid token.");
//    }
//
//    @Test
//    public void testDoGetUserSuccess() throws Exception {
//        String username = "testUser";
//        User user = new User(username, "password", Role.CLIENT);
//        when(request.getPathInfo()).thenReturn("/" + username);
//        when(userService.getUserByUsername(username)).thenReturn(user);
//        when(response.getWriter()).thenReturn(writer);
//
//        userServlet.doGet(request, response);
//
//        verify(response).setStatus(HttpServletResponse.SC_OK);
//        verify(writer).write(new ObjectMapper().writeValueAsString(user));
//    }
//
//    @Test
//    public void testDoGetUserNotFound() throws Exception {
//        String username = "testUser";
//        when(request.getPathInfo()).thenReturn("/" + username);
//        when(userService.getUserByUsername(username)).thenThrow(new NoSuchElementException("User not found"));
//        when(response.getWriter()).thenReturn(writer);
//
//        userServlet.doGet(request, response);
//
//        verify(response).setStatus(HttpServletResponse.SC_NOT_FOUND);
//        verify(writer).write("User not found: " + username);
//    }
//
//    @Test
//    public void testDoGetUsers() throws Exception {
//        User user1 = new User("user1", "password1", Role.CLIENT);
//        User user2 = new User("user2", "password2", Role.CLIENT);
//        when(request.getPathInfo()).thenReturn("/");
//        when(userService.viewUsers()).thenReturn(List.of(user1, user2));
//        when(response.getWriter()).thenReturn(writer);
//
//        userServlet.doGet(request, response);
//
//        verify(response).setStatus(HttpServletResponse.SC_OK);
//        verify(writer).write(new ObjectMapper().writeValueAsString(List.of(user1, user2)));
//    }
//}
