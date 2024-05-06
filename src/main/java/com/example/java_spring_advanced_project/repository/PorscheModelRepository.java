package com.example.java_spring_advanced_project.repository;


import com.example.java_spring_advanced_project.model.entity.PorscheModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PorscheModelRepository extends JpaRepository<PorscheModel, String> {
}
