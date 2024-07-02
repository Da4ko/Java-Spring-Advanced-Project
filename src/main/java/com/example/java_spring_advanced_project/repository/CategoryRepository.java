package com.example.java_spring_advanced_project.repository;


import com.example.java_spring_advanced_project.model.entity.Category;
import com.example.java_spring_advanced_project.model.entity.enums.CategoryName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
   Optional< Category> findByCategory (CategoryName categoryName);
}
