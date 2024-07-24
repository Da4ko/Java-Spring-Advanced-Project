package com.example.java_spring_advanced_project.model.dto.MyCars;

import com.example.java_spring_advanced_project.model.entity.enums.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyPorscheCarDtoTest {

    private MyPorscheCarDto myPorscheCarDto;

    @BeforeEach
    void setUp() {
        myPorscheCarDto = new MyPorscheCarDto();
    }

    @Test
    void testGetAndSetId() {
        String id = "12345";
        myPorscheCarDto.setId(id);
        assertEquals(id, myPorscheCarDto.getId());
    }

    @Test
    void testGetAndSetPorscheModel() {
        PorscheModelsEnum porscheModel = PorscheModelsEnum.Carrera;
        myPorscheCarDto.setPorscheModel(porscheModel);
        assertEquals(porscheModel, myPorscheCarDto.getPorscheModel());
    }

    @Test
    void testGetAndSetHorsePower() {
        int horsePower = 550;
        myPorscheCarDto.setHorsePower(horsePower);
        assertEquals(horsePower, myPorscheCarDto.getHorsePower());
    }

    @Test
    void testGetAndSetReleaseDate() {
        String releaseDate = "2021-05-10";
        myPorscheCarDto.setReleaseDate(releaseDate);
        assertEquals(releaseDate, myPorscheCarDto.getReleaseDate());
    }

    @Test
    void testGetAndSetCategory() {
        CategoryName category = CategoryName.SUV;
        myPorscheCarDto.setCategory(category);
        assertEquals(category, myPorscheCarDto.getCategory());
    }

    @Test
    void testGetAndSetEngine() {
        EngineTypeEnum engine = EngineTypeEnum.Diesel;
        myPorscheCarDto.setEngine(engine);
        assertEquals(engine, myPorscheCarDto.getEngine());
    }

    @Test
    void testGetAndSetTransmission() {
        TransmissionType transmission = TransmissionType.Automatic;
        myPorscheCarDto.setTransmission(transmission);
        assertEquals(transmission, myPorscheCarDto.getTransmission());
    }

    @Test
    void testGetAndSetKilometers() {
        int kilometers = 15000;
        myPorscheCarDto.setKilometers(kilometers);
        assertEquals(kilometers, myPorscheCarDto.getKilometers());
    }

    @Test
    void testGetAndSetBoughtFor() {
        double boughtFor = 80000.0;
        myPorscheCarDto.setBoughtFor(boughtFor);
        assertEquals(boughtFor, myPorscheCarDto.getBoughtFor());
    }

    @Test
    void testGetAndSetCurrencyName() {
        CurrencyName currencyName = CurrencyName.Dollar;
        myPorscheCarDto.setCurrencyName(currencyName);
        assertEquals(currencyName, myPorscheCarDto.getCurrencyName());
    }

    @Test
    void testGetAndSetDescription() {
        String description = "A high-performance Porsche SUV.";
        myPorscheCarDto.setDescription(description);
        assertEquals(description, myPorscheCarDto.getDescription());
    }

    @Test
    void testGetAndSetImageUrl() {
        String imageUrl = "http://example.com/porsche.jpg";
        myPorscheCarDto.setImageUrl(imageUrl);
        assertEquals(imageUrl, myPorscheCarDto.getImageUrl());
    }
}
