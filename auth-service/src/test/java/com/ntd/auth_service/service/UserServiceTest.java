// package com.ntd.auth_service.service;

// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.anyString;
// import static org.mockito.Mockito.when;

// import java.time.LocalDate;

// import org.assertj.core.api.Assertions;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;

// import com.ntd.auth_service.dto.request.UserCreateRequest;
// import com.ntd.auth_service.dto.response.UserResponse;
// import com.ntd.auth_service.entity.User;
// import com.ntd.auth_service.exception.AppException;
// import com.ntd.auth_service.repository.IUserRepository;

// @SpringBootTest
// class UserServiceTest {
//     @Autowired
//     private IUserService userService;

//     @MockBean
//     private IUserRepository userRepository;

//     private UserCreateRequest userCreateRequest;
//     private UserResponse userResponse;
//     private User user;
//     private LocalDate dob;

//     @BeforeEach
//     public void initData() {
//         dob = LocalDate.of(2002, 11, 14);
//         userCreateRequest = UserCreateRequest.builder()
//                 .username("ntd")
//                 .firstName("Duy")
//                 .lastName("Nguyen")
//                 .password("123456")
//                 .dob(dob)
//                 .build();

//         userResponse = UserResponse.builder()
//                 .id(7)
//                 .username("ntd")
//                 .firstName("Duy")
//                 .lastName("Nguyen")
//                 .dob(dob)
//                 .build();

//         user = User.builder()
//                 .id(7)
//                 .username("ntd")
//                 .firstName("Duy")
//                 .lastName("Nguyen")
//                 .dob(dob)
//                 .build();
//     }

//     @Test
//     void createUser_validRequest_success() {
//         //        GIVEN
//         when(userRepository.existsByUsername(anyString())).thenReturn(false);
//         when(userRepository.save(any())).thenReturn(user);

//         //        WHEN
//         var response = userService.createUser(userCreateRequest);

//         //        THEN
//         Assertions.assertThat(response.getId()).isEqualTo(7);
//         Assertions.assertThat(response.getUsername()).isEqualTo("ntd");
//     }

//     @Test
//     void createUser_userExists_fail() {
//         //        GIVEN
//         when(userRepository.existsByUsername(anyString())).thenReturn(true);

//         //        WHEN
//         var exception = assertThrows(AppException.class, () -> userService.createUser(userCreateRequest));

//         //        THEN
//         Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1002);
//     }
// }
