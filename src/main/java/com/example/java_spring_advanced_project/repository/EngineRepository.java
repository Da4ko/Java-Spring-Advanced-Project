package com.example.java_spring_advanced_project.repository;


import com.example.java_spring_advanced_project.model.entity.Engine;
import com.example.java_spring_advanced_project.model.entity.enums.EngineTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EngineRepository extends JpaRepository<Engine, String> {
   Optional <Engine> findByEngineType(EngineTypeEnum engineType);
}
