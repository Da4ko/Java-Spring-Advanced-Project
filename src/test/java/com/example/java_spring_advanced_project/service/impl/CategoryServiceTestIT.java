package com.example.java_spring_advanced_project.service.impl;

import com.example.java_spring_advanced_project.model.entity.enums.CategoryName;
import com.example.java_spring_advanced_project.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
public class CategoryServiceTestIT {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        categoryRepository.deleteAll();
    }

    @Test
    public void testInitCategories() {
        categoryService.initCategories();
        long count = categoryRepository.count();
        assertEquals(CategoryName.values().length, count);
    }
}
