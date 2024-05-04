package com.example.java_spring_advanced_project.model.entity;

import com.example.java_spring_advanced_project.model.entity.enums.CurrencyName;
import jakarta.persistence.*;

@Entity
@Table(name = "currencies")
public class Currency extends BaseEntity {


    private CurrencyName currency;
    private String originCountry;
    private double toEuro;

    public Currency() {
    }
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public CurrencyName getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyName currency) {
        this.currency = currency;
    }
    @Column(nullable = false)
    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }
    @Column(nullable = false)
    public double getToEuro() {
        return toEuro;
    }

    public void setToEuro(double toEuro) {
        this.toEuro = toEuro;
    }
}