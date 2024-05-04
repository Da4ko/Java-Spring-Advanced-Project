package com.example.java_spring_advanced_project.model.entity;

import com.example.java_spring_advanced_project.model.entity.enums.PorscheModelsEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "porsche_cars")
public class PorscheCar extends Car {

    private PorscheModelsEnum porscheModel;
    private User owner;
    public PorscheCar() {
        super();
    }
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public PorscheModelsEnum getPorscheModel() {
        return porscheModel;
    }

    public void setPorscheModel(PorscheModelsEnum porscheModel) {
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