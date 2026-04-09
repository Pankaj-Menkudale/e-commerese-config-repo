package com.codelab.api_gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtFilter implements GlobalFilter, Ordered {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public int getOrder() {
        return -1; // 🔥 very important
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        System.out.println("JWT FILTER CALLED: " + exchange.getRequest().getURI().getPath());

        String path = exchange.getRequest().getURI().getPath();

        System.out.println("Incoming Request: " + path);

        // ✅ Allow auth APIs
        if (path.startsWith("/auth")) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);
        System.out.println("Authorization Header: " + authHeader);

        // ❌ No token
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("No Authorization Header");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        // ❌ Invalid token
        if (!jwtUtil.validateToken(token)) {
            System.out.println("Invalid Token");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        else {
            System.out.println("Token Validated: " + token);
        }

        // ✅ Extract email
        String email = jwtUtil.extractEmail(token);

        System.out.println("Email from token: " + email);
        // ✅ Forward to downstream
        ServerWebExchange mutatedExchange = exchange.mutate()
                .request(builder -> builder.header("X-User-Email", email))
                .build();

        return chain.filter(mutatedExchange);
    }
}