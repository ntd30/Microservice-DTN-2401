package com.ntd.auth_service.dto.request;

import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import com.ntd.auth_service.validator.DobConstraint;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserCreateRequest {
    @NotBlank(message = "USERNAME_NOT_NULL")
    @Size(min = 3, max = 50, message = "USERNAME_INVALID")
    private String username;

    @NotBlank(message = "PASSWORD_NOT_NULL")
    @Size(min = 6, message = "INVALID_PASSWORD")
    private String password;

    private String firstName;

    private String lastName;

    @PastOrPresent(message = "INVALID_DOB")
    @DobConstraint(min = 16, message = "INVALID_DOB_2")
    private LocalDate dob;

    //    @Pattern(regexp = "ADMIN|USER", message = "Role must be ADMIN or USER")
    private Set<String> roles;
}
