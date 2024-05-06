package com.example.java_spring_advanced_project.model.entity;

import com.example.java_spring_advanced_project.model.entity.enums.BmwModelsEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "bmw_cars")
public class BmwCar extends Car {

    private BmwModel bmwModel;
    private User owner;

    public BmwCar() {
        super();
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bmw_model_id", nullable = false)
    public BmwModel getBmwModel() {
        return bmwModel;
    }

    public void setBmwModel(BmwModel bmwModel) {
        this.bmwModel = bmwModel;
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