package com.example.java_spring_advanced_project.model.dto.MyCars;

import com.example.java_spring_advanced_project.model.entity.AudiCar;
import com.example.java_spring_advanced_project.model.entity.enums.*;

public class MyAudiCarDto {
    private String Id;
    private AudiModelsEnum audiModel;
    private int horsePower;
    private String releaseDate;
    private CategoryName category;
    private EngineTypeEnum engine;
    private TransmissionType transmission;
    private int kilometers;
    private double boughtFor;
    private CurrencyName currencyName;
    private String description;
    private String imageUrl;

    public MyAudiCarDto() {
    }
    public MyAudiCarDto(AudiCar audiCar) {
        this.Id = audiCar.getId();
        this.audiModel = audiCar.getAudiModel().getModel();
        this.horsePower = audiCar.getHorsepowers();
        this.releaseDate = audiCar.getReleaseDate().toString();
        this.category = audiCar.getCategory().getCategory();
        this.engine = audiCar.getEngine().getEngineType();
        this.transmission = audiCar.getTransmission().getTransmission();
        this.kilometers = audiCar.getKilometers();
        this.boughtFor = audiCar.getPrice();
        this.currencyName = audiCar.getCurrency().getCurrency();
        this.description = audiCar.getDescription();
        this.imageUrl = audiCar.getImageUrl();

    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public AudiModelsEnum getAudiModel() {
        return audiModel;
    }

    public void setAudiModel(AudiModelsEnum audiModel) {
        this.audiModel = audiModel;
    }

    public int getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public CategoryName getCategory() {
        return category;
    }

    public void setCategory(CategoryName category) {
        this.category = category;
    }

    public EngineTypeEnum getEngine() {
        return engine;
    }

    public void setEngine(EngineTypeEnum engine) {
        this.engine = engine;
    }

    public TransmissionType getTransmission() {
        return transmission;
    }

    public void setTransmission(TransmissionType transmission) {
        this.transmission = transmission;
    }

    public int getKilometers() {
        return kilometers;
    }

    public void setKilometers(int kilometers) {
        this.kilometers = kilometers;
    }

    public double getBoughtFor() {
        return boughtFor;
    }

    public void setBoughtFor(double boughtFor) {
        this.boughtFor = boughtFor;
    }

    public CurrencyName getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(CurrencyName currencyName) {
        this.currencyName = currencyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
