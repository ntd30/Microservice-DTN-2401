package com.ntd.auth_service.dto.response;

import lombok.*;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class AuthenticationResponse {
    private boolean authenticated;
    private String token;
}
