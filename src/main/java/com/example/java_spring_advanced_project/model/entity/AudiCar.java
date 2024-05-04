package com.example.java_spring_advanced_project.model.entity;

import com.example.java_spring_advanced_project.model.entity.enums.AudiModelsEnum;

import jakarta.persistence.*;

@Entity
@Table(name = "audi_cars")
public class AudiCar extends Car {


    private AudiModelsEnum audiModel;
    private User owner;

    public AudiCar() {
        super();
    }
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public AudiModelsEnum getAudiModel() {
        return audiModel;
    }

    public void setAudiModel(AudiModelsEnum audiModel) {
        this.audiModel = audiModel;
    }
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}