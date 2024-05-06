package com.example.java_spring_advanced_project.repository;


import com.example.java_spring_advanced_project.model.entity.BmwModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BmwModelRepository extends JpaRepository<BmwModel, String> {
}
