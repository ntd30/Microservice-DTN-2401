package com.ntd.auth_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ntd.auth_service.entity.User;

public interface IUserRepository extends JpaRepository<User, Long> {
    @Query("FROM User u WHERE u.firstName LIKE CONCAT('%', ?1, '%')")
    List<User> search(String firstName);

    Page<User> findByFirstNameContaining(String firstName, Pageable pageable);

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}
