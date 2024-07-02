package com.example.java_spring_advanced_project.model.dto.OthersCars;

import com.example.java_spring_advanced_project.model.entity.BmwCar;
import com.example.java_spring_advanced_project.model.entity.enums.*;

public class OthersBmwCarDto {
    private String id;
    private BmwModelsEnum bmwModel;
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
    private String ownerUsername;

    public OthersBmwCarDto() {
    }

    public OthersBmwCarDto(BmwCar bmwCar) {
        this.id = bmwCar.getId();
        this.bmwModel = bmwCar.getBmwModel().getModel();
        this.horsePower = bmwCar.getHorsepowers();
        this.releaseDate = bmwCar.getReleaseDate().toString();
        this.category = bmwCar.getCategory().getCategory();
        this.engine = bmwCar.getEngine().getEngineType();
        this.transmission = bmwCar.getTransmission().getTransmission();
        this.kilometers = bmwCar.getKilometers();
        this.boughtFor = bmwCar.getPrice();
        this.currencyName = bmwCar.getCurrency().getCurrency();
        this.description = bmwCar.getDescription();
        this.imageUrl = bmwCar.getImageUrl();
        this.ownerUsername = bmwCar.getOwner().getUsername();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BmwModelsEnum getBmwModel() {
        return bmwModel;
    }

    public void setBmwModel(BmwModelsEnum bmwModel) {
        this.bmwModel = bmwModel;
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

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }
}
