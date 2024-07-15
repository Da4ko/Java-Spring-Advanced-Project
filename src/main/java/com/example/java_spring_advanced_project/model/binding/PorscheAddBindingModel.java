package com.example.java_spring_advanced_project.model.binding;

import com.example.java_spring_advanced_project.model.entity.enums.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class PorscheAddBindingModel {
    private PorscheModelsEnum porscheModel;
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

    @NotNull(message = "{porsche.validation.model.notNull}")
    public PorscheModelsEnum getPorscheModel() {
        return porscheModel;
    }

    public void setPorscheModel(PorscheModelsEnum porscheModel) {
        this.porscheModel = porscheModel;
    }

    @Positive(message = "{porsche.validation.horsePower.positive}")
    @NotNull(message = "{porsche.validation.horsePower.notNull}")
    public int getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    @NotNull(message = "{porsche.validation.imageUrl.notNull}")
    @Size(min = 10, max = 50000, message = "{porsche.validation.imageUrl.size}")
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @NotNull(message = "{porsche.validation.releaseDate.notNull}")
    @PastOrPresent(message = "{porsche.validation.releaseDate.pastOrPresent}")
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @NotNull(message = "{porsche.validation.category.notNull}")
    public CategoryName getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(CategoryName categoryName) {
        this.categoryName = categoryName;
    }

    @NotNull(message = "{porsche.validation.engineType.notNull}")
    public EngineTypeEnum getEngineType() {
        return engineType;
    }

    public void setEngineType(EngineTypeEnum engineType) {
        this.engineType = engineType;
    }

    @NotNull(message = "{porsche.validation.transmission.notNull}")
    public TransmissionType getTransmission() {
        return transmission;
    }

    public void setTransmission(TransmissionType transmission) {
        this.transmission = transmission;
    }

    @Positive(message = "{porsche.validation.kilometers.positive}")
    @NotNull(message = "{porsche.validation.kilometers.notNull}")
    public int getKilometers() {
        return kilometers;
    }

    public void setKilometers(int kilometers) {
        this.kilometers = kilometers;
    }

    @NotNull(message = "{porsche.validation.currencyName.notNull}")
    public CurrencyName getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(CurrencyName currencyName) {
        this.currencyName = currencyName;
    }

    @Positive(message = "{porsche.validation.price.positive}")
    @NotNull(message = "{porsche.validation.price.notNull}")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @NotNull(message = "{porsche.validation.description.notNull}")
    @Size(min = 2, max = 500, message = "{porsche.validation.description.size}")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
