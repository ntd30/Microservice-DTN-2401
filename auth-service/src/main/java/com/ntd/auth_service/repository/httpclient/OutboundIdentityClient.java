package com.ntd.auth_service.repository.httpclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

import com.ntd.auth_service.dto.request.ExchangeTokenRequest;
import com.ntd.auth_service.dto.response.ExchangeTokenResponse;

import feign.QueryMap;

@FeignClient(name = "outbound-indentity", url = "https://oauth2.googleapis.com")
public interface OutboundIdentityClient {
    @PostMapping(value = "/token", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ExchangeTokenResponse exchangeToken(@QueryMap ExchangeTokenRequest request);
}
