package com.example.java_spring_advanced_project.model.binding;

import com.example.java_spring_advanced_project.model.entity.enums.*;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class AudiAddBindingModel {

    private AudiModelsEnum audiModel;
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

    @NotNull(message = "{audi.model.required}")
    public AudiModelsEnum getAudiModel() {
        return audiModel;
    }

    public void setAudiModel(AudiModelsEnum audiModel) {
        this.audiModel = audiModel;
    }

    @Positive(message = "{horsepower.positive}")
    @NotNull(message = "{horsepower.required}")
    public int getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    @NotNull(message = "{image.required}")
    @Size(min = 10, max = 50000, message = "{image.size}")
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @NotNull(message = "{release.date.required}")
    @PastOrPresent(message = "{release.date.future}")
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @NotNull(message = "{category.required}")
    public CategoryName getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(CategoryName categoryName) {
        this.categoryName = categoryName;
    }

    @NotNull(message = "{engine.type.required}")
    public EngineTypeEnum getEngineType() {
        return engineType;
    }

    public void setEngineType(EngineTypeEnum engineType) {
        this.engineType = engineType;
    }

    @NotNull(message = "{transmission.type.required}")
    public TransmissionType getTransmission() {
        return transmission;
    }

    public void setTransmission(TransmissionType transmission) {
        this.transmission = transmission;
    }

    @Positive(message = "{kilometers.positive}")
    @NotNull(message = "{kilometers.required}")
    public int getKilometers() {
        return kilometers;
    }

    public void setKilometers(int kilometers) {
        this.kilometers = kilometers;
    }

    @NotNull(message = "{currency.required}")
    public CurrencyName getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(CurrencyName currencyName) {
        this.currencyName = currencyName;
    }

    @Positive(message = "{price.positive}")
    @NotNull(message = "{price.required}")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @NotNull(message = "{description.required}")
    @Size(min = 2, max = 500, message = "{description.size}")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
