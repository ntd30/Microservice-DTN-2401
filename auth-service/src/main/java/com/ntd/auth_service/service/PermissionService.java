package com.ntd.auth_service.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import com.ntd.auth_service.dto.request.PermissionRequest;
import com.ntd.auth_service.dto.response.PermissionResponse;
import com.ntd.auth_service.entity.Permission;
import com.ntd.auth_service.repository.IPermissionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PermissionService {
    private final IPermissionRepository permissionRepository;
    private final ModelMapper modelMapper;

    public PermissionResponse create(PermissionRequest request) {
        Permission permission = modelMapper.map(request, Permission.class);
        permissionRepository.save(permission);
        return modelMapper.map(permission, PermissionResponse.class);
    }

    public List<PermissionResponse> getAll() {
        var permissions = permissionRepository.findAll();
        return modelMapper.map(permissions, new TypeToken<List<PermissionResponse>>() {}.getType());
    }

    public void delete(String permission) {
        permissionRepository.deleteById(permission);
    }
}
