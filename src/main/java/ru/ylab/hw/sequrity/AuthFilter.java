package ru.ylab.hw.sequrity;

import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Slf4j
@WebFilter("/api/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String requestURI = req.getRequestURI();

        if (requestURI.endsWith("/register") || requestURI.endsWith("/login")) {
            try {
                chain.doFilter(request, response);
            } catch (IOException | ServletException e) {
                log.error("Error during filtering", e);
            }
            return;
        }

        String authHeader = req.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String token = authHeader.substring(7);
        try {
            Claims claims = JwtUtil.validateToken(token);
            String username = Objects.requireNonNull(claims).getSubject();
            List<String> roles = claims.get("roles", List.class);
            request.setAttribute("username", username);
            request.setAttribute("roles", roles);

            String method = req.getMethod();

            if (!isAuthorized(roles, requestURI, method)) {
                res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            chain.doFilter(request, response);
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private boolean isAuthorized(List<String> roles, String uri, String method) {
        if (roles.contains("ADMIN")) {
            return true;
        }

        if (roles.contains("MANAGER")) {
            return switch (uri) {
                case "/api/cars", "/api/orders", "/api/users/register", "/api/users" -> true;
                default -> method.equals("POST") || method.equals("PUT") || method.equals("DELETE") || method.equals("GET");
            };
        }

        if (roles.contains("CLIENT")) {
            return switch (uri) {
                case "/api/cars", "/api/orders", "/api/users/register", "/api/users/login" -> method.equals("POST") || method.equals("GET");
                default -> false;
            };
        }

        return false;
    }
}
