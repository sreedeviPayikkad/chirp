package com.intuit.chirp.feeds;

import com.intuit.chirp.feeds.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class FeedServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeedServiceApplication.class, args);
	}

}
