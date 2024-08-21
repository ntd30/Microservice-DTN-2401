package com.ntd.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ntd.auth_service.entity.Permission;

public interface IPermissionRepository extends JpaRepository<Permission, String> {}
