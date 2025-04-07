package com.ceciliasuarez.project.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthFilter extends OncePerRequestFilter {

    @Value("${admin.secret}")
    private String adminSecret;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        if ("POST".equalsIgnoreCase(request.getMethod()) ||
                "PUT".equalsIgnoreCase(request.getMethod()) ||
                "DELETE".equalsIgnoreCase(request.getMethod())) {

            String token = request.getHeader("x-admin-token");

            if (token == null || !token.equals(adminSecret)) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("No autorizado");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
