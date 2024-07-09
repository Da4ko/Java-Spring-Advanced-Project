package com.example.java_spring_advanced_project.repository;

import com.example.java_spring_advanced_project.model.entity.AudiCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AudiCarsRepository extends JpaRepository<AudiCar, String> {
    void deleteAudiCarById(String id);
}
