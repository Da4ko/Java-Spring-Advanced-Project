package com.example.java_spring_advanced_project.service.impl;

import com.example.java_spring_advanced_project.model.entity.Category;
import com.example.java_spring_advanced_project.model.entity.enums.CategoryName;
import com.example.java_spring_advanced_project.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository mockCategoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void initCategoriesWhenRepositoryIsEmpty() {
        // Arrange
        when(mockCategoryRepository.count()).thenReturn(0L); // Repository is empty

        // Act
        categoryService.initCategories();

        // Assert
        // Verify that save is called for each CategoryName value
        verify(mockCategoryRepository, times(CategoryName.values().length)).save(any(Category.class));

        // Verify that save is called with the correct Category objects
        for (CategoryName categoryName : CategoryName.values()) {
            Category expectedCategory = new Category(categoryName, "description for " + categoryName.name());
            verify(mockCategoryRepository).save(argThat(category ->
                    category.getCategory() == categoryName &&
                            category.getDescription().equals("description for " + categoryName.name())
            ));
        }
    }

    @Test
    void initCategoriesWhenRepositoryIsNotEmpty() {
        // Arrange
        when(mockCategoryRepository.count()).thenReturn(5L); // Repository already contains categories

        // Act
        categoryService.initCategories();

        // Assert
        // Verify that save is never called since the repository is not empty
        verify(mockCategoryRepository, never()).save(any(Category.class));
    }
}
