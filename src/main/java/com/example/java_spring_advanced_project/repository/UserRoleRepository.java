package com.example.java_spring_advanced_project.repository;

import com.example.java_spring_advanced_project.model.entity.AudiModel;
import com.example.java_spring_advanced_project.model.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
}
