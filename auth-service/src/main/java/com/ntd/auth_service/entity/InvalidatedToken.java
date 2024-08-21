package com.ntd.auth_service.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "invalidated-tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvalidatedToken {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "expiry_time")
    private Date expiryTime;
}
