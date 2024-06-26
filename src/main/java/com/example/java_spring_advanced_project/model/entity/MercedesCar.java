package com.example.java_spring_advanced_project.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "mercedes_cars")
public class MercedesCar extends Car {

    private MercedesModel mercedesModel;
    private UserEntity owner;

    public MercedesCar() {
        super();
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mercedes_model_id", nullable = false)
    public MercedesModel getMercedesModel() {
        return mercedesModel;
    }

    public void setMercedesModel(MercedesModel mercedesModel) {
        this.mercedesModel = mercedesModel;
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