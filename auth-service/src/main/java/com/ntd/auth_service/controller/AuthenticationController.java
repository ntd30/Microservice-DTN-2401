package com.ntd.auth_service.controller;

import java.text.ParseException;

import jakarta.validation.Valid;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.nimbusds.jose.JOSEException;
import com.ntd.auth_service.dto.request.*;
import com.ntd.auth_service.dto.response.ApiResponse;
import com.ntd.auth_service.dto.response.AuthenticationResponse;
import com.ntd.auth_service.dto.response.IntrospectResponse;
import com.ntd.auth_service.dto.response.UserResponse;
import com.ntd.auth_service.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/create-password")
    public ApiResponse<Void> createPassword(@RequestBody @Valid PasswordCreateRequest request) {
        authenticationService.createPassword(request);
        return ApiResponse.<Void>builder()
                .message("Password has been created, you could use it to log-in")
                .build();
    }

    @PostMapping("/outbound/authentication")
    public ApiResponse<AuthenticationResponse> outboundAuthenticate(@RequestParam String code) {
        var result = authenticationService.outboundAuthenticate(code);
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }

    @PostMapping("/register")
    public ApiResponse<UserResponse> register(@RequestBody @Valid UserCreateRequest request) {
        // log.info("Create user controller");
        return ApiResponse.<UserResponse>builder()
                .message("Created!")
                .result(authenticationService.register(request))
                .build();
    }

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder().result(result).build();
    }

    @PostMapping("/refresh")
    public ApiResponse<AuthenticationResponse> authenticate(@RequestBody RefreshRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(request);
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder().build();
    }
}
