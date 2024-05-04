package com.example.java_spring_advanced_project.model.entity;

import com.example.java_spring_advanced_project.model.entity.enums.BmwModelsEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "bmw_cars")
public class BmwCar extends Car {

    private BmwModelsEnum bmwModel;
    private User owner;
    public BmwCar() {
        super();
    }
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public BmwModelsEnum getBmwModel() {
        return bmwModel;
    }

    public void setBmwModel(BmwModelsEnum bmwModel) {
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