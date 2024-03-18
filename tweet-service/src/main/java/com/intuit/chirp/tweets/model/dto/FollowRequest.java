package com.intuit.chirp.tweets.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record FollowRequest(
        @NotNull(message = "id cannot be null")
        Long id) {
}
