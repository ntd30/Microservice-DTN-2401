package com.ntd.auth_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PasswordCreateRequest {
    @NotBlank(message = "PASSWORD_NOT_NULL")
    @Size(min = 6, message = "INVALID_PASSWORD")
    private String password;
}
