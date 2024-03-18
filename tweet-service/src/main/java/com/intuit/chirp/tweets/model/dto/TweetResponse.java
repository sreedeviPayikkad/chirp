package com.intuit.chirp.tweets.model.dto;

import lombok.Builder;

@Builder
public record TweetResponse(Long id, Long userId, String content) {

}
