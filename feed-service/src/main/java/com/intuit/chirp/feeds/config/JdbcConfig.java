package com.intuit.chirp.feeds.config;

import com.intuit.chirp.feeds.model.domain.TokenData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;

import java.util.UUID;

@Configuration
public class JdbcConfig {
    @Bean
    BeforeConvertCallback<TokenData> beforeConvertCallback() {
        return (token) -> {
            if (token.getId() == null) {
                token.setId(UUID.randomUUID());
            }
            return token;
        };
    }
}
