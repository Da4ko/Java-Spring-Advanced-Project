package com.example.java_spring_advanced_project.model.dto.OthersCars;

import com.example.java_spring_advanced_project.model.entity.MercedesCar;
import com.example.java_spring_advanced_project.model.entity.enums.*;

public class OthersMercedesCarDto {
    private String id;
    private MercedesModelsEnum mercedesModel;
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

    public OthersMercedesCarDto() {
    }

    public OthersMercedesCarDto(MercedesCar mercedesCar) {
        this.id = mercedesCar.getId();
        this.mercedesModel = mercedesCar.getMercedesModel().getModel();
        this.horsePower = mercedesCar.getHorsepowers();
        this.releaseDate = mercedesCar.getReleaseDate().toString();
        this.category = mercedesCar.getCategory().getCategory();
        this.engine = mercedesCar.getEngine().getEngineType();
        this.transmission = mercedesCar.getTransmission().getTransmission();
        this.kilometers = mercedesCar.getKilometers();
        this.boughtFor = mercedesCar.getPrice();
        this.currencyName = mercedesCar.getCurrency().getCurrency();
        this.description = mercedesCar.getDescription();
        this.imageUrl = mercedesCar.getImageUrl();
        this.ownerUsername = mercedesCar.getOwner().getUsername();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MercedesModelsEnum getMercedesModel() {
        return mercedesModel;
    }

    public void setMercedesModel(MercedesModelsEnum mercedesModel) {
        this.mercedesModel = mercedesModel;
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
