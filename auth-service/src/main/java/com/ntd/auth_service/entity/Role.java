package com.ntd.auth_service.entity;

import java.util.Set;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany
    private Set<Permission> permissions;
}
