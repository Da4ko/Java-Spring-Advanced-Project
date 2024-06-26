package com.example.java_spring_advanced_project.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "audi_cars")
public class AudiCar extends Car {

    private AudiModel audiModel;
    private UserEntity owner;

    public AudiCar() {
        super();
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "audi_model_id", nullable = false)
    public AudiModel getAudiModel() {
        return audiModel;
    }

    public void setAudiModel(AudiModel audiModel) {
        this.audiModel = audiModel;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }
}