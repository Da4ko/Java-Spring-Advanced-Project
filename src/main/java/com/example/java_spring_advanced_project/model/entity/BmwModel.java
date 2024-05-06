package com.example.java_spring_advanced_project.model.entity;

import com.example.java_spring_advanced_project.model.entity.enums.BmwModelsEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "bmw_models")
public class BmwModel extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BmwModelsEnum model;

    @Column(nullable = true)
    private String description;

    public BmwModel() {
    }

    public BmwModel(BmwModelsEnum model, String description) {
        this.model = model;
        this.description = description;
    }

    public BmwModelsEnum getModel() {
        return model;
    }

    public void setModel(BmwModelsEnum model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}