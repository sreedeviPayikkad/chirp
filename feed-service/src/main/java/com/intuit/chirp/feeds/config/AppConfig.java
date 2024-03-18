package com.intuit.chirp.feeds.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${FEED_SIZE}")
    private int feedSize;

    public int getFeedSize() {
        return feedSize;
    }
}
