package ru.ylab.hw.sequrity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;

import static org.mockito.Mockito.*;
class JwtFilterTest {

    private JwtFilter adminFilter;
    private JwtFilter managerFilter;
    private JwtFilter userFilter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        adminFilter = new JwtFilter(Collections.singletonList("ADMIN"));
        managerFilter = new JwtFilter(Collections.singletonList("MANAGER"));
        userFilter = new JwtFilter(Collections.singletonList("USER"));

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);
    }

    @Test
    void testAdminAccessWithAdminRole() throws IOException, ServletException {
        String adminToken = "Bearer " + JwtUtil.generateToken("adminUser", Collections.singletonList("ADMIN"));

        when(request.getHeader("Authorization")).thenReturn(adminToken);

        adminFilter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verify(response, never()).setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

    @Test
    void testManagerAccessWithManagerRole() throws IOException, ServletException {
        String managerToken = "Bearer " + JwtUtil.generateToken("managerUser", Collections.singletonList("MANAGER"));

        when(request.getHeader("Authorization")).thenReturn(managerToken);

        managerFilter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verify(response, never()).setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

    @Test
    void testUserAccessWithUserRole() throws IOException, ServletException {
        String userToken = "Bearer " + JwtUtil.generateToken("normalUser", Collections.singletonList("USER"));

        when(request.getHeader("Authorization")).thenReturn(userToken);

        userFilter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verify(response, never()).setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

    @Test
    void testAdminAccessWithInsufficientPermissions() throws IOException, ServletException {
        String userToken = "Bearer " + JwtUtil.generateToken("normalUser", Collections.singletonList("USER"));

        when(request.getHeader("Authorization")).thenReturn(userToken);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);

        adminFilter.doFilter(request, response, filterChain);

        verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
        verify(response.getWriter()).write("Insufficient permissions");
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    void testManagerAccessWithInsufficientPermissions() throws IOException, ServletException {
        String userToken = "Bearer " + JwtUtil.generateToken("normalUser", Collections.singletonList("USER"));

        when(request.getHeader("Authorization")).thenReturn(userToken);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);

        managerFilter.doFilter(request, response, filterChain);

        verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
        verify(response.getWriter()).write("Insufficient permissions");
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    void testUserAccessWithNoToken() throws IOException, ServletException {
        when(request.getHeader("Authorization")).thenReturn(null);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);

        userFilter.doFilter(request, response, filterChain);

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response.getWriter()).write("Missing or invalid Authorization header");
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    void testUserAccessWithInvalidToken() throws IOException, ServletException {
        String invalidToken = "Bearer invalidToken";

        when(request.getHeader("Authorization")).thenReturn(invalidToken);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);

        userFilter.doFilter(request, response, filterChain);

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response.getWriter()).write("Invalid JWT token");
        verify(filterChain, never()).doFilter(request, response);
    }
}