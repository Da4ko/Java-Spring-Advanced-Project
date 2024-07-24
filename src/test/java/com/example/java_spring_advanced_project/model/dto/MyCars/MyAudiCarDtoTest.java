package com.example.java_spring_advanced_project.model.dto.MyCars;

import com.example.java_spring_advanced_project.model.entity.enums.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyAudiCarDtoTest {

    private MyAudiCarDto myAudiCarDto;

    @BeforeEach
    void setUp() {
        myAudiCarDto = new MyAudiCarDto();
    }

    @Test
    void testGetAndSetId() {
        String id = "12345";
        myAudiCarDto.setId(id);
        assertEquals(id, myAudiCarDto.getId());
    }

    @Test
    void testGetAndSetAudiModel() {
        AudiModelsEnum audiModel = AudiModelsEnum.A4;
        myAudiCarDto.setAudiModel(audiModel);
        assertEquals(audiModel, myAudiCarDto.getAudiModel());
    }

    @Test
    void testGetAndSetHorsePower() {
        int horsePower = 150;
        myAudiCarDto.setHorsePower(horsePower);
        assertEquals(horsePower, myAudiCarDto.getHorsePower());
    }

    @Test
    void testGetAndSetReleaseDate() {
        String releaseDate = "2020-01-01";
        myAudiCarDto.setReleaseDate(releaseDate);
        assertEquals(releaseDate, myAudiCarDto.getReleaseDate());
    }

    @Test
    void testGetAndSetCategory() {
        CategoryName category = CategoryName.SUV;
        myAudiCarDto.setCategory(category);
        assertEquals(category, myAudiCarDto.getCategory());
    }

    @Test
    void testGetAndSetEngine() {
        EngineTypeEnum engine = EngineTypeEnum.Diesel;
        myAudiCarDto.setEngine(engine);
        assertEquals(engine, myAudiCarDto.getEngine());
    }

    @Test
    void testGetAndSetTransmission() {
        TransmissionType transmission = TransmissionType.Automatic;
        myAudiCarDto.setTransmission(transmission);
        assertEquals(transmission, myAudiCarDto.getTransmission());
    }

    @Test
    void testGetAndSetKilometers() {
        int kilometers = 10000;
        myAudiCarDto.setKilometers(kilometers);
        assertEquals(kilometers, myAudiCarDto.getKilometers());
    }

    @Test
    void testGetAndSetBoughtFor() {
        double boughtFor = 30000.0;
        myAudiCarDto.setBoughtFor(boughtFor);
        assertEquals(boughtFor, myAudiCarDto.getBoughtFor());
    }

    @Test
    void testGetAndSetCurrencyName() {
        CurrencyName currencyName = CurrencyName.Dollar;
        myAudiCarDto.setCurrencyName(currencyName);
        assertEquals(currencyName, myAudiCarDto.getCurrencyName());
    }

    @Test
    void testGetAndSetDescription() {
        String description = "A very nice car.";
        myAudiCarDto.setDescription(description);
        assertEquals(description, myAudiCarDto.getDescription());
    }

    @Test
    void testGetAndSetImageUrl() {
        String imageUrl = "http://example.com/image.jpg";
        myAudiCarDto.setImageUrl(imageUrl);
        assertEquals(imageUrl, myAudiCarDto.getImageUrl());
    }
}
