package com.vti.api_gateway.configuration;

import java.util.Arrays;
import java.util.List;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vti.api_gateway.dto.response.ApiResponse;
import com.vti.api_gateway.service.AuthService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter implements GlobalFilter, Ordered {
    private final AuthService authService;
    private final ObjectMapper objectMapper;

    private String[] publicEndpoints = {"/auth/login", "/auth/register"};

    @Override
    public int getOrder() {
        return -1;
    }

    private boolean isPublicEndpoint(ServerHttpRequest request) {
        return Arrays.stream(publicEndpoints).anyMatch(s -> request.getURI().getPath().matches(s));
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("Enter authentication filter***********");

        if (isPublicEndpoint(exchange.getRequest())) return chain.filter(exchange);

        // Get token from authorization header
        List<String> authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);

        if (CollectionUtils.isEmpty(authHeader)) {
            return unauthenticated(exchange.getResponse());
        }

        // verify token
        String token = authHeader.get(0).replace("Bearer ", "");
        System.out.println("Token: " + token);

        // Delegate auth service
        return authService.introspect(token).flatMap(t -> {
            if (t.getResult().isValid()) {
                return chain.filter(exchange);
            } else {
                return unauthenticated(exchange.getResponse());
            }
        }).onErrorResume(t -> unauthenticated(exchange.getResponse()));
    }

    private Mono<Void> unauthenticated(ServerHttpResponse response) {
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(1401)
                .message("Unauthenticated")
                .build();

        String body = null;
        try {
            body = objectMapper.writeValueAsString(apiResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        return response.writeWith(Mono.just(response.bufferFactory().wrap(body.getBytes())));
    }
    
}
