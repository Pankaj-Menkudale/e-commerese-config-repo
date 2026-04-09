package com.codelab.api_gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        return http
                .csrf(csrf -> csrf.disable())
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/auth/**",
                                "/orders/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/product-service/v3/api-docs/**",
                                "/auth-service/v3/api-docs/**").permitAll()
                        .anyExchange().permitAll() // ✅ IMPORTANT
                )
                .build();
    }
}