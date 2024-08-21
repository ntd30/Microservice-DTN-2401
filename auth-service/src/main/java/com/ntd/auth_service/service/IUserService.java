package com.ntd.auth_service.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ntd.auth_service.dto.request.UserUpdateRequest;
import com.ntd.auth_service.dto.response.UserResponse;
import com.ntd.auth_service.entity.User;

public interface IUserService {
    //    ======================================== GET ========================================
    //    Get all users
    List<User> getAllUsers();

    //    Get users with paging
    Page<User> getUsers(Pageable pageable);

    //    Get user my info
    UserResponse getMyInfo();

    //    Get user by id
    User getUserById(long id);

    //    Check user exists by id
    boolean isUserExistsById(long id);

    //    Check user exists by username
    boolean isUserExistsByUsername(String username);

    //    Get user by name containing
    List<User> search(String firstName);

    Page<User> search(String firstName, Pageable pageable);

    //    ======================================== POST ========================================
    //    Create a new user

    //    ======================================== PUT ========================================
    //    Update user by id
    UserResponse updateUser(long id, UserUpdateRequest form);

    //    ======================================== DELETE ========================================
    //    Delete user by id
    void deleteUser(long id);

    //    Delete multiple users
    void deleteMultipleUsers(List<Long> ids);
}
