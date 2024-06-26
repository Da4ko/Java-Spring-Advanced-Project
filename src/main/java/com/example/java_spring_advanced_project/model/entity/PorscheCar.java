package com.example.java_spring_advanced_project.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "porsche_cars")
public class PorscheCar extends Car {

    private PorscheModel porscheModel;
    private UserEntity owner;

    public PorscheCar() {
        super();
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "porsche_model_id", nullable = false)
    public PorscheModel getPorscheModel() {
        return porscheModel;
    }

    public void setPorscheModel(PorscheModel porscheModel) {
        this.porscheModel = porscheModel;
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