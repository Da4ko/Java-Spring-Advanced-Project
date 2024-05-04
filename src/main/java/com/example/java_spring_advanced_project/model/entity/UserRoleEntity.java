package com.example.java_spring_advanced_project.model.entity;

import com.example.java_spring_advanced_project.model.entity.enums.RoleEnum;
import jakarta.persistence.*;

@Table(name = "roles")
@Entity
public class UserRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    public Long getId() {
        return id;
    }

    public UserRoleEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public RoleEnum getRole() {
        return role;
    }

    public UserRoleEntity setRole(RoleEnum role) {
        this.role = role;
        return this;
    }
}