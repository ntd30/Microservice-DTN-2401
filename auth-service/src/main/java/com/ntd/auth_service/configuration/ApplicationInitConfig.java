package com.ntd.auth_service.configuration;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ntd.auth_service.entity.Permission;
import com.ntd.auth_service.entity.Role;
import com.ntd.auth_service.entity.User;
import com.ntd.auth_service.repository.IPermissionRepository;
import com.ntd.auth_service.repository.IRoleRepository;
import com.ntd.auth_service.repository.IUserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class ApplicationInitConfig {
    private final PasswordEncoder passwordEncoder;
    private static final String ADMIN = "admin"; // Compliant

    @Bean
    ApplicationRunner applicationRunner(
            IUserRepository userRepository,
            IRoleRepository roleRepository,
            IPermissionRepository permissionRepository) {
        return args -> {
            if (userRepository.findByUsername(ADMIN).isEmpty()) {
                Set<Permission> permissions = new HashSet<>();
                Permission permission1 = Permission.builder()
                        .name("CREATE_DATA")
                        .description("Create data")
                        .build();
                Permission permission2 = Permission.builder()
                        .name("UPDATE_DATA")
                        .description("Update data")
                        .build();
                Permission permission3 = Permission.builder()
                        .name("DELETE_DATA")
                        .description("Delete data")
                        .build();
                permissionRepository.save(permission1);
                permissionRepository.save(permission2);
                permissionRepository.save(permission3);
                permissions.add(permission1);
                permissions.add(permission2);
                permissions.add(permission3);

                Set<Role> roles = new HashSet<>();
                Role role = Role.builder()
                        .name("ADMIN")
                        .description("Admin role")
                        .permissions(permissions)
                        .build();
                roleRepository.save(role);
                roles.add(role);

                User user = User.builder()
                        .username(ADMIN)
                        .password(passwordEncoder.encode("admin"))
                        .roles(roles)
                        .build();
                userRepository.save(user);
                log.warn("admin user has been created with default password: admin, please change it!");

                if (roleRepository.findByName("USER").isEmpty()) {
                    Set<Permission> permissionsOfUser = new HashSet<>();
                    permissionsOfUser.add(permission1);
                    Role roleUser = Role.builder()
                            .name("USER")
                            .description("User role")
                            .permissions(permissionsOfUser)
                            .build();
                    roleRepository.save(roleUser);
                    log.warn("Role user has been created!");
                }
            }
        };
    }
}
