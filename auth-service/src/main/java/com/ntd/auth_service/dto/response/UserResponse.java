package com.ntd.auth_service.dto.response;

import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserResponse {
    private long id;
    private String username;
    private String firstName;
    private String lastName;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dob;

    private boolean noPassword;
    private Set<RoleResponse> roles;
}
