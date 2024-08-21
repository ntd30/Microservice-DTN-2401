package com.ntd.auth_service.dto.request;

import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import com.ntd.auth_service.validator.DobConstraint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserUpdateRequest {
    @NotBlank(message = "PASSWORD_NOT_NULL")
    @Size(min = 6, max = 50, message = "INVALID_PASSWORD")
    private String password;

    private String firstName;
    private String lastName;

    @PastOrPresent(message = "INVALID_DOB")
    @DobConstraint(min = 18, message = "INVALID_DOB_2")
    private LocalDate dob;

    private Set<String> roles;
}
