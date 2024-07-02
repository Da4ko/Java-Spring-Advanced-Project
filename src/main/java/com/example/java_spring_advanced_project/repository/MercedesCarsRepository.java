package com.example.java_spring_advanced_project.repository;

import com.example.java_spring_advanced_project.model.entity.MercedesCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MercedesCarsRepository extends JpaRepository<MercedesCar, String> {
}
