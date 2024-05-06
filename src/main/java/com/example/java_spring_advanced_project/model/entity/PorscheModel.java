package com.example.java_spring_advanced_project.model.entity;

import com.example.java_spring_advanced_project.model.entity.enums.PorscheModelsEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "porsche_models")
public class PorscheModel extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PorscheModelsEnum model;

    @Column(nullable = false)
    private String description;

    public PorscheModel(PorscheModelsEnum model, String description) {
        this.model = model;
        this.description = description;
    }

    public PorscheModel() {
    }

    public PorscheModelsEnum getModel() {
        return model;
    }

    public void setModel(PorscheModelsEnum model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}