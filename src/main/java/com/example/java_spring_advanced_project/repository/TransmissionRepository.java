package com.example.java_spring_advanced_project.repository;


import com.example.java_spring_advanced_project.model.entity.Transmission;
import com.example.java_spring_advanced_project.model.entity.enums.TransmissionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransmissionRepository extends JpaRepository<Transmission, String> {
  Optional < Transmission > findByTransmission(TransmissionType transmission);
}
