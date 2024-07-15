package com.example.java_spring_advanced_project.model.binding;

import com.example.java_spring_advanced_project.model.entity.enums.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class MercedesAddBindingModel {
    private MercedesModelsEnum mercedesModel;
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

    @NotNull(message = "{mercedes.validation.model.notNull}")
    public MercedesModelsEnum getMercedesModel() {
        return mercedesModel;
    }

    public void setMercedesModel(MercedesModelsEnum mercedesModel) {
        this.mercedesModel = mercedesModel;
    }

    @Positive(message = "{mercedes.validation.horsePower.positive}")
    @NotNull(message = "{mercedes.validation.horsePower.notNull}")
    public int getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    @NotNull(message = "{mercedes.validation.imageUrl.notNull}")
    @Size(min = 10, max = 50000, message = "{mercedes.validation.imageUrl.size}")
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @NotNull(message = "{mercedes.validation.releaseDate.notNull}")
    @PastOrPresent(message = "{mercedes.validation.releaseDate.pastOrPresent}")
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @NotNull(message = "{mercedes.validation.category.notNull}")
    public CategoryName getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(CategoryName categoryName) {
        this.categoryName = categoryName;
    }

    @NotNull(message = "{mercedes.validation.engineType.notNull}")
    public EngineTypeEnum getEngineType() {
        return engineType;
    }

    public void setEngineType(EngineTypeEnum engineType) {
        this.engineType = engineType;
    }

    @NotNull(message = "{mercedes.validation.transmission.notNull}")
    public TransmissionType getTransmission() {
        return transmission;
    }

    public void setTransmission(TransmissionType transmission) {
        this.transmission = transmission;
    }

    @Positive(message = "{mercedes.validation.kilometers.positive}")
    @NotNull(message = "{mercedes.validation.kilometers.notNull}")
    public int getKilometers() {
        return kilometers;
    }

    public void setKilometers(int kilometers) {
        this.kilometers = kilometers;
    }

    @NotNull(message = "{mercedes.validation.currencyName.notNull}")
    public CurrencyName getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(CurrencyName currencyName) {
        this.currencyName = currencyName;
    }

    @Positive(message = "{mercedes.validation.price.positive}")
    @NotNull(message = "{mercedes.validation.price.notNull}")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @NotNull(message = "{mercedes.validation.description.notNull}")
    @Size(min = 2, max = 500, message = "{mercedes.validation.description.size}")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
