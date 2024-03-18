package com.intuit.chirp.feeds.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class OncePerRequestLogger extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestUrl = request.getRequestURI();
        if(!requestUrl.contains("actuator")) {
            log.info("The endpoint requested : {}", request.getRequestURI());
        }
        filterChain.doFilter(request, response);
    }
}
