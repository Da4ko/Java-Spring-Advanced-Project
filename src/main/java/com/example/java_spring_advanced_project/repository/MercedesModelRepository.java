package com.example.java_spring_advanced_project.repository;


import com.example.java_spring_advanced_project.model.entity.MercedesModel;
import com.example.java_spring_advanced_project.model.entity.enums.MercedesModelsEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MercedesModelRepository extends JpaRepository<MercedesModel, String> {
    Optional<MercedesModel> findByModel(MercedesModelsEnum mercedesModel);
}
