package com.example.java_spring_advanced_project.model.binding;

import com.example.java_spring_advanced_project.model.entity.enums.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class BmwAddBindingModel {
    private BmwModelsEnum bmwModel;
    private int horsePower;
    private String imageUrl;
    private LocalDate releaseDate;
    private CategoryName categoryName;
    private EngineTypeEnum engineType;
    private TransmissionType transmission;
    private int kilometers;
    private CurrencyName currencyName;
    private double price;
    private String description;

    @NotNull(message = "{NotNull.bmwModel}")
    public BmwModelsEnum getBmwModel() {
        return bmwModel;
    }

    public void setBmwModel(BmwModelsEnum bmwModel) {
        this.bmwModel = bmwModel;
    }

    @Positive(message = "{Positive.horsePower}")
    @NotNull(message = "{NotNull.horsePower}")
    public int getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    @NotNull(message = "{NotNull.imageUrl}")
    @Size(min = 10, max=50000, message = "{Size.imageUrl}")
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @NotNull(message = "{NotNull.releaseDate}")
    @PastOrPresent(message = "{PastOrPresent.releaseDate}")
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @NotNull(message = "{NotNull.categoryName}")
    public CategoryName getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(CategoryName categoryName) {
        this.categoryName = categoryName;
    }

    @NotNull(message = "{NotNull.engineType}")
    public EngineTypeEnum getEngineType() {
        return engineType;
    }

    public void setEngineType(EngineTypeEnum engineType) {
        this.engineType = engineType;
    }

    @NotNull(message = "{NotNull.transmission}")
    public TransmissionType getTransmission() {
        return transmission;
    }

    public void setTransmission(TransmissionType transmission) {
        this.transmission = transmission;
    }

    @Positive(message = "{Positive.kilometers}")
    @NotNull(message = "{NotNull.kilometers}")
    public int getKilometers() {
        return kilometers;
    }

    public void setKilometers(int kilometers) {
        this.kilometers = kilometers;
    }

    @NotNull(message = "{NotNull.currencyName}")
    public CurrencyName getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(CurrencyName currencyName) {
        this.currencyName = currencyName;
    }

    @Positive(message = "{Positive.price}")
    @NotNull(message = "{NotNull.price}")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @NotNull(message = "{NotNull.description}")
    @Size(min = 2, max=500, message = "{Size.description}")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
