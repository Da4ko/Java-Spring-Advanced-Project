package com.example.java_spring_advanced_project.model.entity;

import com.example.java_spring_advanced_project.model.entity.enums.MercedesModelsEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "mercedes_cars")
public class MercedesCar extends Car {

    private MercedesModelsEnum mercedesModel;
    private User owner;

    public MercedesCar() {
        super();
    }
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public MercedesModelsEnum getMercedesModel() {
        return mercedesModel;
    }

    public void setMercedesModel(MercedesModelsEnum mercedesModel) {
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