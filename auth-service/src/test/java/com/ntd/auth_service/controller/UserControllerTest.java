// package com.ntd.auth_service.controller;

// import java.time.LocalDate;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.ArgumentMatchers;
// import org.mockito.Mockito;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
// import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
// import com.ntd.auth_service.dto.request.UserCreateRequest;
// import com.ntd.auth_service.dto.response.UserResponse;
// import com.ntd.auth_service.service.IUserService;

// import lombok.extern.slf4j.Slf4j;

// @SpringBootTest
// @Slf4j
// @AutoConfigureMockMvc
// class UserControllerTest {
//     @Autowired
//     private MockMvc mockMvc;

//     @MockBean
//     private IUserService userService;

//     private UserCreateRequest request;
//     private UserResponse response;
//     private LocalDate dob;

//     @BeforeEach
//     public void initData() {
//         dob = LocalDate.of(2002, 11, 14);

//         request = UserCreateRequest.builder()
//                 .username("ntd")
//                 .firstName("Duy")
//                 .lastName("Nguyen")
//                 .password("123456")
//                 .dob(dob)
//                 .build();

//         response = UserResponse.builder()
//                 .id(7)
//                 .username("ntd")
//                 .firstName("Duy")
//                 .lastName("Nguyen")
//                 .dob(dob)
//                 .build();
//     }

//     @Test
//     void createUser_validRequest_success() throws Exception {
//         //        GIVEN
//         ObjectMapper objectMapper = new ObjectMapper();
//         objectMapper.registerModule(new JavaTimeModule());
//         String content = objectMapper.writeValueAsString(request);

//         Mockito.when(userService.createUser(ArgumentMatchers.any())).thenReturn(response);

//         //        WHEN, THEN
//         mockMvc.perform(MockMvcRequestBuilders.post("/users")
//                         .contentType(MediaType.APPLICATION_JSON_VALUE)
//                         .content(content))
//                 .andExpect(MockMvcResultMatchers.status().isOk())
//                 .andExpect(MockMvcResultMatchers.jsonPath("code").value("1000"))
//                 .andExpect(MockMvcResultMatchers.jsonPath("result.id").value("7"));
//     }

//     @Test
//     void createUser_UsernameInvalid_fail() throws Exception {
//         //        GIVEN
//         request.setUsername("jd");
//         ObjectMapper objectMapper = new ObjectMapper();
//         objectMapper.registerModule(new JavaTimeModule());
//         String content = objectMapper.writeValueAsString(request);

//         //        WHEN, THEN
//         mockMvc.perform(MockMvcRequestBuilders.post("/users")
//                         .contentType(MediaType.APPLICATION_JSON_VALUE)
//                         .content(content))
//                 .andExpect(MockMvcResultMatchers.status().isBadRequest())
//                 .andExpect(MockMvcResultMatchers.jsonPath("code").value("1003"))
//                 .andExpect(MockMvcResultMatchers.jsonPath("message").value("Username must be at least 3
// characters"));
//     }
// }
