package ru.ylab.hw.sequrity;

import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JwtFilter implements Filter {
    private List<String> allowedRoles;

    public JwtFilter(List<String> allowedRoles) {
        this.allowedRoles = allowedRoles;
    }

    @Override
    public void init(FilterConfig filterConfig) {
        String allowedRolesParam = filterConfig.getInitParameter("allowedRoles");
        this.allowedRoles = Arrays.asList(allowedRolesParam.split(","));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String authHeader = req.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("Missing or invalid Authorization header");
            return;
        }

        String token = authHeader.substring(7);
        Claims claims = JwtUtil.validateToken(token);

        if (claims == null) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("Invalid JWT token");
            return;
        }

        String username = claims.getSubject();
        List<String> roles = claims.get("roles", List.class);

        if (roles.stream().noneMatch(allowedRoles::contains)) {
            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
            res.getWriter().write("Insufficient permissions");
            return;
        }

        req.setAttribute("username", username);
        chain.doFilter(request, response);
    }
}