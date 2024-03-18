package com.intuit.chirp.tweets.model.dto;

import java.util.List;

public record FollowResponse(List<Long> followingIds) {
}
