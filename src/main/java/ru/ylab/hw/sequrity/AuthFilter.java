package ru.ylab.hw.sequrity;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();

        if (requestURI.endsWith("/register") || requestURI.endsWith("/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = httpRequest.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String token = authHeader.substring(7);
        try {
            Claims claims = JwtUtil.validateToken(token);
            String username = Objects.requireNonNull(claims).getSubject();
            List<String> roles = claims.get("roles", List.class);
            httpRequest.setAttribute("username", username);
            httpRequest.setAttribute("roles", roles);

            if (!isAuthorized(roles, requestURI, httpRequest.getMethod())) {
                httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("JWT token validation error", e);
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
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
