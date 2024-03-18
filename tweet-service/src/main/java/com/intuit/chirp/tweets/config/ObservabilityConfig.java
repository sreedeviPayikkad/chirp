package com.intuit.chirp.tweets.config;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObservabilityConfig {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${spring.datasource.hikari.pool-name}")
    private String hikariCp;

    @Bean
    MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> registry
                .config()
                .commonTags("application", applicationName)
                .commonTags("hikaricp", hikariCp)
                .commonTags("instance", "default"); // todo: should be app instance id - need to check this when deploying to k8s
    }
}
