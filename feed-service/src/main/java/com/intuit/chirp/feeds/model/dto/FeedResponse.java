package com.intuit.chirp.feeds.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record FeedResponse(
        @NonNull
        List<TweetResponse> tweets,
        @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$", message = "invalud UUID format")
        UUID nextToken) {
}

