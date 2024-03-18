package com.intuit.chirp.authserver.dto;

public record TokenResponse(String accessToken, long expiresIn) {
}
