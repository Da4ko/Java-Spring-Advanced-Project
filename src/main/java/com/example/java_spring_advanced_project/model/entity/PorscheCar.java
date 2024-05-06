package com.example.java_spring_advanced_project.model.entity;

import com.example.java_spring_advanced_project.model.entity.enums.PorscheModelsEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "porsche_cars")
public class PorscheCar extends Car {

    private PorscheModel porscheModel;
    private User owner;

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
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}