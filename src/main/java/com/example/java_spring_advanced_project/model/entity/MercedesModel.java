package com.example.java_spring_advanced_project.model.entity;

import com.example.java_spring_advanced_project.model.entity.enums.MercedesModelsEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "mercedes_models")
public class MercedesModel extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MercedesModelsEnum model;

    @Column(nullable = true)
    private String description;

    public MercedesModel() {
    }

    public MercedesModel(MercedesModelsEnum model, String description) {
        this.model = model;
        this.description = description;
    }

    public MercedesModelsEnum getModel() {
        return model;
    }

    public void setModel(MercedesModelsEnum model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}