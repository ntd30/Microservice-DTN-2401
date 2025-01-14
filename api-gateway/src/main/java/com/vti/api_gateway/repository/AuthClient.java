package com.vti.api_gateway.repository;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

import com.vti.api_gateway.dto.request.IntrospectRequest;
import com.vti.api_gateway.dto.response.ApiResponse;
import com.vti.api_gateway.dto.response.IntrospectResponse;

import reactor.core.publisher.Mono;

public interface AuthClient {
    @PostExchange(url = "/auth/introspect", contentType = MediaType.APPLICATION_JSON_VALUE)
    Mono<ApiResponse<IntrospectResponse>> introspect(@RequestBody IntrospectRequest request);
}
