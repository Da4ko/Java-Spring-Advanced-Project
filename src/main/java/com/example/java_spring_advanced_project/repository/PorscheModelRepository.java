package com.example.java_spring_advanced_project.repository;


import com.example.java_spring_advanced_project.model.entity.PorscheModel;
import com.example.java_spring_advanced_project.model.entity.enums.PorscheModelsEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PorscheModelRepository extends JpaRepository<PorscheModel, String> {
    Optional<PorscheModel> findByModel(PorscheModelsEnum porscheModel);
}
