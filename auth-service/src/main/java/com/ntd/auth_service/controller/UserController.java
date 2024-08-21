package com.ntd.auth_service.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.ntd.auth_service.dto.request.UserUpdateRequest;
import com.ntd.auth_service.dto.response.ApiResponse;
import com.ntd.auth_service.dto.response.UserResponse;
import com.ntd.auth_service.entity.User;
import com.ntd.auth_service.service.IUserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@Validated
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    private final ModelMapper modelMapper;

    //    ======================================== GET ========================================
    //    Get all users
    @GetMapping("/all")
    public ApiResponse<List<UserResponse>> getAllUsers() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("username: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        List<User> users = userService.getAllUsers();
        List<UserResponse> userResponses = modelMapper.map(users, new TypeToken<List<UserResponse>>() {}.getType());

        return ApiResponse.<List<UserResponse>>builder().result(userResponses).build();
    }

    //    Get users with paging
    @GetMapping
    public Page<UserResponse> getUsers(Pageable pageable) {
        Page<User> users = userService.getUsers(pageable);
        List<UserResponse> dtos = modelMapper.map(users.getContent(), new TypeToken<List<UserResponse>>() {}.getType());
        return new PageImpl<>(dtos, pageable, users.getTotalElements());
    }

    //    Get my info
    @GetMapping("/my-info")
    public ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    //    Get user by id
    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserById(@PathVariable long id) {
        User user = userService.getUserById(id);
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        return ApiResponse.<UserResponse>builder().result(userResponse).build();
    }

    //    Check user exists
    @GetMapping("/exists/{id}")
    public boolean isUserExistsById(@PathVariable long id) {
        return userService.isUserExistsById(id);
    }

    //    Search
    @GetMapping("/search/{firstName}")
    //    public List<UserDTO> search(@PathVariable String firstName) {
    //        List<User> users = userService.search(firstName);
    //        return modelMapper.map(users, new TypeToken<List<UserDTO>>(){}.getType());
    //    }
    public Page<UserResponse> search(@PathVariable String firstName, @PageableDefault(size = 5) Pageable pageable) {
        Page<User> users = userService.search(firstName, pageable);
        List<UserResponse> dtos = modelMapper.map(users.getContent(), new TypeToken<List<UserResponse>>() {}.getType());
        return new PageImpl<>(dtos, pageable, users.getTotalElements());
    }

    //    ======================================== POST ========================================
    //    Create user
    //    @PostMapping
    //    public ResponseEntity<Object> createUser (@RequestBody @Valid CreateUserRequest form) {
    //        userService.createUser(form);
    //        return new ResponseEntity<>("Created", HttpStatus.CREATED);
    //    }

    //    ======================================== PUT ========================================
    //    Update user
    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateUser(@PathVariable long id, @RequestBody @Valid UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .message("Updated!")
                .result(userService.updateUser(id, request))
                .build();
    }

    //    ======================================== DELETE ========================================
    //    Delete a user
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    //    Delete multiple users
    @DeleteMapping("/multiple/{ids}")
    public ResponseEntity<Object> deleteMultipleUsers(@PathVariable List<Long> ids) {
        userService.deleteMultipleUsers(ids);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }
}
