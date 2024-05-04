package com.example.java_spring_advanced_project.model.entity;
import com.example.java_spring_advanced_project.model.entity.enums.CategoryName;
import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryName category;

    @Column(nullable = false)
    private String description;

    public Category() {
    }
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public CategoryName getCategory() {
        return category;
    }

    public void setCategory(CategoryName category) {
        this.category = category;
    }
    @Column(nullable = true, columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}