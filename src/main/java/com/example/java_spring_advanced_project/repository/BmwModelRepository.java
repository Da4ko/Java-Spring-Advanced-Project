package com.example.java_spring_advanced_project.repository;


import com.example.java_spring_advanced_project.model.entity.BmwModel;
import com.example.java_spring_advanced_project.model.entity.enums.BmwModelsEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BmwModelRepository extends JpaRepository<BmwModel, String> {
    Optional<BmwModel> findByModel(BmwModelsEnum bmwModel);

}
