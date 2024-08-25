package ru.ylab.hw.sequrity;

import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class AuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        if (requestURI.endsWith("/register") || requestURI.endsWith("/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String token = authHeader.substring(7);
        try {
            Claims claims = JwtUtil.validateToken(token);
            String username = Objects.requireNonNull(claims).getSubject();
            List<String> roles = claims.get("roles", List.class);
            request.setAttribute("username", username);
            request.setAttribute("roles", roles);

            if (!isAuthorized(roles, requestURI, request.getMethod())) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("JWT token validation error", e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
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
                case "/api/cars", "/api/orders", "/api/users/register", "/api/users/login" -> method.equals("POST")
                        || method.equals("GET");
                default -> false;
            };
        }

        return false;
    }
}
