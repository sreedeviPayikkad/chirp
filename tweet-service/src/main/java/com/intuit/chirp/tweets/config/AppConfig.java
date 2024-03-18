package com.intuit.chirp.tweets.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Value("${tweet-minSize}")
    private String minSize;

    @Value("${tweet-maxSize}")
    private String maxSize;

    public int getMinTweetSize() {
        return Integer.parseInt(minSize);
    }

    public int getMaxTweetSize() {
        return Integer.parseInt(maxSize);
    }

}
