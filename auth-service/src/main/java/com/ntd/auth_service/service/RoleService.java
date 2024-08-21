package com.ntd.auth_service.service;

import java.util.HashSet;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import com.ntd.auth_service.dto.request.RoleRequest;
import com.ntd.auth_service.dto.response.RoleResponse;
import com.ntd.auth_service.entity.Role;
import com.ntd.auth_service.repository.IPermissionRepository;
import com.ntd.auth_service.repository.IRoleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleService {
    private final IRoleRepository roleRepository;

    private final IPermissionRepository permissionRepository;

    private final ModelMapper modelMapper;

    public RoleResponse create(RoleRequest request) {
        var role = modelMapper.map(request, Role.class);

        var permissions = permissionRepository.findAllById(request.getPermissions());

        role.setPermissions(new HashSet<>(permissions));

        roleRepository.save(role);

        return modelMapper.map(role, RoleResponse.class);
    }

    public List<RoleResponse> getAll() {
        var roles = roleRepository.findAll();
        return modelMapper.map(roles, new TypeToken<List<RoleResponse>>() {}.getType());
    }

    public void delete(String role) {
        roleRepository.deleteById(role);
    }
}
