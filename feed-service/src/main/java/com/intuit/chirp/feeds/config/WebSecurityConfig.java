package com.intuit.chirp.feeds.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final RsaKeyProperties rsaKeyProperties;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/actuator/health")
                                .permitAll()
                                .requestMatchers("/actuator/prometheus")
                                .permitAll()
                                .anyRequest()
                                .fullyAuthenticated()
                )
                .csrf(AbstractHttpConfigurer::disable) // csrf is not required for rest api - not accessed from UI
                .oauth2ResourceServer(configurer -> configurer.jwt(Customizer.withDefaults()))
                .build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeyProperties.publicKey()).build();
    }
}
