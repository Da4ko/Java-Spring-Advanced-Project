package com.example.java_spring_advanced_project.model.entity;

import com.example.java_spring_advanced_project.model.entity.enums.MercedesModelsEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "mercedes_cars")
public class MercedesCar extends Car {

    private MercedesModel mercedesModel;
    private User owner;

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
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}