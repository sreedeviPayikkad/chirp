package com.intuit.chirp.tweets.model.dto;

import com.intuit.chirp.tweets.validations.TweetContent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record TweetRequest(
        @NotNull(message = "content cannot be null")
        @NotEmpty(message = "content cannot be empty")
       // @Size(min = 1, max = 240)
        @TweetContent String content) {}

