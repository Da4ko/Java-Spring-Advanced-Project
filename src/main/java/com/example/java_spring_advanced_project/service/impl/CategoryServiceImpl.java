package com.example.java_spring_advanced_project.service.impl;

import com.example.java_spring_advanced_project.model.entity.Category;
import com.example.java_spring_advanced_project.model.entity.enums.CategoryName;
import com.example.java_spring_advanced_project.repository.CategoryRepository;
import com.example.java_spring_advanced_project.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void initCategories() {
        if (categoryRepository.count() == 0) {
            Arrays.stream(CategoryName.values())
                    .forEach(categoryName -> {
                        Category category = new Category(categoryName, "description for " + categoryName.name());
                        categoryRepository.save(category);
                    });
        }
    }
}
