package com.vti.api_gateway.dto.request;

import org.hibernate.validator.constraints.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddProductRequest {
    private UUID id;
    private String productName;
    private String productCode;
    private String description;
}
