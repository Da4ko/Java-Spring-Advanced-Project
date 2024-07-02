package com.example.java_spring_advanced_project.model.dto.MyCars;

import com.example.java_spring_advanced_project.model.entity.PorscheCar;
import com.example.java_spring_advanced_project.model.entity.enums.*;

public class MyPorscheCarDto {
    private String id;
    private PorscheModelsEnum porscheModel;
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

    public MyPorscheCarDto() {
    }

    public MyPorscheCarDto(PorscheCar porscheCar) {
        this.id = porscheCar.getId();
        this.porscheModel = porscheCar.getPorscheModel().getModel();
        this.horsePower = porscheCar.getHorsepowers();
        this.releaseDate = porscheCar.getReleaseDate().toString();
        this.category = porscheCar.getCategory().getCategory();
        this.engine = porscheCar.getEngine().getEngineType();
        this.transmission = porscheCar.getTransmission().getTransmission();
        this.kilometers = porscheCar.getKilometers();
        this.boughtFor = porscheCar.getPrice();
        this.currencyName = porscheCar.getCurrency().getCurrency();
        this.description = porscheCar.getDescription();
        this.imageUrl = porscheCar.getImageUrl();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PorscheModelsEnum getPorscheModel() {
        return porscheModel;
    }

    public void setPorscheModel(PorscheModelsEnum porscheModel) {
        this.porscheModel = porscheModel;
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
