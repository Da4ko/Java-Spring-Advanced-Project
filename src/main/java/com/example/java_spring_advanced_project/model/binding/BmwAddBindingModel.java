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

    @NotNull(message = "You must select model")
    public BmwModelsEnum getBmwModel() {
        return bmwModel;
    }

    public void setBmwModel(BmwModelsEnum bmwModel) {
        this.bmwModel = bmwModel;
    }
    @Positive(message = "horsepower cannot be negative or zero")
    @NotNull(message = "Horsepower cannot be empty")
    public int getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }
    @NotNull(message = "The car must have image")
    @Size(min = 10, max=50000, message = "Image must be at least 10 characters")
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    @NotNull(message = "Release date cannot be null!")
    @PastOrPresent(message = "The date cannot be in the future")
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
    @NotNull(message = "You must select category")
    public CategoryName getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(CategoryName categoryName) {
        this.categoryName = categoryName;
    }
    @NotNull(message = "You must select engine type")
    public EngineTypeEnum getEngineType() {
        return engineType;
    }

    public void setEngineType(EngineTypeEnum engineType) {
        this.engineType = engineType;
    }
    @NotNull(message = "You must select transmission type")
    public TransmissionType getTransmission() {
        return transmission;
    }

    public void setTransmission(TransmissionType transmission) {
        this.transmission = transmission;
    }
    @PositiveOrZero(message = "kilometers cannot be negative")
    @NotNull(message = "You must enter the kilometers")
    public int getKilometers() {
        return kilometers;
    }

    public void setKilometers(int kilometers) {
        this.kilometers = kilometers;
    }
    @NotNull(message = "You must select currency for your price")
    public CurrencyName getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(CurrencyName currencyName) {
        this.currencyName = currencyName;
    }
    @PositiveOrZero(message = "kilometers cannot be negative")
    @NotNull(message = "You must enter the price")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    @NotNull(message = "You must enter the description")
    @Size(min = 2, max=50, message = "Description must be between 2 and 50 characters")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
