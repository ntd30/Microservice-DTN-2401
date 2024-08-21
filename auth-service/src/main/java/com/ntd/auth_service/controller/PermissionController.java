package com.ntd.auth_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.ntd.auth_service.dto.request.PermissionRequest;
import com.ntd.auth_service.dto.response.ApiResponse;
import com.ntd.auth_service.dto.response.PermissionResponse;
import com.ntd.auth_service.service.PermissionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@Slf4j
public class PermissionController {
    private final PermissionService permissionService;

    @PostMapping
    public ApiResponse<PermissionResponse> create(@RequestBody PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .message("Created!")
                .result(permissionService.create(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<PermissionResponse>> getAll() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getAll())
                .build();
    }

    @DeleteMapping("/{permission}")
    public ApiResponse<Void> delete(@PathVariable String permission) {
        permissionService.delete(permission);
        return ApiResponse.<Void>builder().message("Deleted!").build();
    }
}
