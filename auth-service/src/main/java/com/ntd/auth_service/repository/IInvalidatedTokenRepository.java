package com.ntd.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ntd.auth_service.entity.InvalidatedToken;

public interface IInvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {}
