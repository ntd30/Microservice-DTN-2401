package com.ntd.auth_service.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ntd.auth_service.dto.request.RoleRequest;
import com.ntd.auth_service.dto.request.UserCreateRequest;
import com.ntd.auth_service.dto.request.UserUpdateRequest;
import com.ntd.auth_service.entity.Role;
import com.ntd.auth_service.entity.User;

@Configuration
public class ConfigBean {
    @Bean
    public ModelMapper init() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(RoleRequest.class, Role.class).addMappings(mapping -> mapping.skip(Role::setPermissions));
        modelMapper.typeMap(UserUpdateRequest.class, User.class).addMappings(mapping -> mapping.skip(User::setRoles));
        modelMapper.typeMap(UserCreateRequest.class, User.class).addMappings(mapping -> mapping.skip(User::setRoles));
        return modelMapper;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
