package com.ntd.auth_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ntd.auth_service.entity.Role;

public interface IRoleRepository extends JpaRepository<Role, String> {

    Optional<Role> findByName(String string);
}
