package com.example.java_spring_advanced_project.model.entity;


import com.example.java_spring_advanced_project.model.entity.enums.TransmissionType;
import jakarta.persistence.*;


@Entity
@Table(name = "transmissions")
public class Transmission extends BaseEntity {


    private TransmissionType transmission;


    private String description;

    public Transmission() {
    }

    public Transmission(TransmissionType transmission, String description) {
        this.transmission = transmission;
        this.description = description;
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public TransmissionType getTransmission() {
        return transmission;
    }

    public void setTransmission(TransmissionType transmission) {
        this.transmission = transmission;
    }
    @Column(nullable = true, columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}