package com.vti.api_gateway.service;

import org.springframework.stereotype.Service;

import com.vti.api_gateway.dto.request.IntrospectRequest;
import com.vti.api_gateway.dto.response.ApiResponse;
import com.vti.api_gateway.dto.response.IntrospectResponse;
import com.vti.api_gateway.repository.AuthClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthClient authClient;
    
    public Mono<ApiResponse<IntrospectResponse>> introspect(String token) {
        return authClient.introspect(IntrospectRequest.builder().token(token).build());
    }
}
