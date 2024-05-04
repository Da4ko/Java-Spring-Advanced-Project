package com.example.java_spring_advanced_project.model.entity;

import com.example.java_spring_advanced_project.model.entity.enums.EngineTypeEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "engines")
public class Engine extends BaseEntity {


    private EngineTypeEnum engineType;


    private String description;


    public Engine() {
    }
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public EngineTypeEnum getEngineType() {
        return engineType;
    }

    public void setEngineType(EngineTypeEnum engineType) {
        this.engineType = engineType;
    }
    @Column(nullable = true, columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}