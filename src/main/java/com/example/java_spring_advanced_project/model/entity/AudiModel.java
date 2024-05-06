package com.example.java_spring_advanced_project.model.entity;

import com.example.java_spring_advanced_project.model.entity.enums.AudiModelsEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "audi_models")
public class AudiModel extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AudiModelsEnum model;

    @Column(nullable = true)
    private String description;

    public AudiModel() {
    }

    public AudiModel(AudiModelsEnum model, String description) {
        this.model = model;
        this.description = description;
    }

    public AudiModelsEnum getModel() {
        return model;
    }

    public void setModel(AudiModelsEnum model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}