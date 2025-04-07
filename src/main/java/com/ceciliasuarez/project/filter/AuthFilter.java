package com.ceciliasuarez.project.filter;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;

@Component
public class AuthFilter implements Filter {

    @Value("${admin.secret}")
    private String adminSecret;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if (httpRequest.getMethod().equalsIgnoreCase("POST") ||
                httpRequest.getMethod().equalsIgnoreCase("PUT") ||
                httpRequest.getMethod().equalsIgnoreCase("DELETE")) {

            String token = httpRequest.getHeader("x-admin-token");
            if (token == null || !token.equals(adminSecret)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No autorizado");
            }
        }

        chain.doFilter(request, response);
    }
}
