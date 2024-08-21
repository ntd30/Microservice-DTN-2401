package com.ntd.auth_service.service;

import java.util.HashSet;
import java.util.List;

import jakarta.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ntd.auth_service.dto.request.UserUpdateRequest;
import com.ntd.auth_service.dto.response.UserResponse;
import com.ntd.auth_service.entity.User;
import com.ntd.auth_service.exception.AppException;
import com.ntd.auth_service.exception.ErrorCode;
import com.ntd.auth_service.repository.IRoleRepository;
import com.ntd.auth_service.repository.IUserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final IRoleRepository roleRepository;

    //    ======================================== GET ========================================
    //    Get all users
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        log.info("In method get all users");
        return userRepository.findAll();
    }

    //    Get users with paging
    @Override
    //    @PreAuthorize("hasRole('ADMIN')")
    // @PreAuthorize("hasAuthority('REJECT_POST')")
    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    //    Get my information
    @Override
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        var userResponse = modelMapper.map(user, UserResponse.class);
        userResponse.setNoPassword(!StringUtils.hasText(user.getPassword()));

        return userResponse;
    }

    //    Get user by id
    @PostAuthorize("returnObject.username == authentication.name")
    @Override
    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    //    Check user exists
    @Override
    public boolean isUserExistsById(long id) {
        return userRepository.existsById(id);
    }

    //    Check user exists by username
    @Override
    public boolean isUserExistsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    //    Get user by name containing
    @Override
    public List<User> search(String firstName) {
        return userRepository.search(firstName);
    }

    @Override
    public Page<User> search(String firstName, Pageable pageable) {
        return userRepository.findByFirstNameContaining(firstName, pageable);
    }

    //    ======================================== POST ========================================
    //    Create a new user

    //    ======================================== PUT ========================================
    //    Update user by id
    @Override
    @Transactional
    public UserResponse updateUser(long id, UserUpdateRequest request) {
        User user = modelMapper.map(request, User.class);
        user.setId(id);

        User oleUser = getUserById(user.getId());

        user.setUsername(oleUser.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        return modelMapper.map(userRepository.save(user), UserResponse.class);
    }

    //    ======================================== DELETE ========================================
    //    Delete user by id
    @Override
    @Transactional
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    //    Delete multiple users
    @Override
    @Transactional
    public void deleteMultipleUsers(List<Long> ids) {
        for (long id : ids) {
            if (isUserExistsById(id)) {
                userRepository.deleteById(id);
            }
        }
    }
}
