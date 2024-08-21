package com.ntd.auth_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.ntd.auth_service.dto.request.RoleRequest;
import com.ntd.auth_service.dto.response.ApiResponse;
import com.ntd.auth_service.dto.response.RoleResponse;
import com.ntd.auth_service.service.RoleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@Slf4j
public class RoleController {
    private final RoleService roleService;

    @PostMapping
    public ApiResponse<RoleResponse> create(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .message("Created!")
                .result(roleService.create(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build();
    }

    @DeleteMapping("/{role}")
    public ApiResponse<Void> delete(@PathVariable String role) {
        roleService.delete(role);
        return ApiResponse.<Void>builder().message("Deleted!").build();
    }
}
