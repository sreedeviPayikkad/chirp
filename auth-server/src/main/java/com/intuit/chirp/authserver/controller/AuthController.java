package com.intuit.chirp.authserver.controller;

import com.intuit.chirp.authserver.dto.TokenResponse;
import com.intuit.chirp.authserver.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;

@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
public class AuthController {

    private final TokenService tokenService;
    @GetMapping
    public TokenResponse token(Authentication authentication, HttpServletRequest request) {
        Jwt jwt = tokenService.generateToken(authentication, request);
        return new TokenResponse(jwt.getTokenValue(), Duration.between(Instant.now(), jwt.getExpiresAt()).getSeconds());
    }

}
