package com.example.java_spring_advanced_project.repository;

import com.example.java_spring_advanced_project.model.entity.AudiModel;
import com.example.java_spring_advanced_project.model.entity.enums.AudiModelsEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AudiModelRepository extends JpaRepository<AudiModel, String> {

    Optional<AudiModel> findByModel(AudiModelsEnum model);
}
