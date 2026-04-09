package com.codelab.api_gateway.filter;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component   // VERY IMPORTANT
public class LoggingFilter implements GlobalFilter {

    private static final Logger logger = LogManager.getLogger(LoggingFilter.class);

    @Override
    public Mono<Void> filter(org.springframework.web.server.ServerWebExchange exchange,
                             org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();
        String method = exchange.getRequest().getMethod().name();

        logger.info("Incoming request -> Method: {}, Path: {}", method, path);

        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {
                    logger.info("Response sent for -> Path: {}", path);
                }));
    }
}
