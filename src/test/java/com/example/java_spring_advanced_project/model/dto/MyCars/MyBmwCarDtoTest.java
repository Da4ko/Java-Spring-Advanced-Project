package com.example.java_spring_advanced_project.model.dto.MyCars;

import com.example.java_spring_advanced_project.model.entity.enums.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyBmwCarDtoTest {

    private MyBmwCarDto myBmwCarDto;

    @BeforeEach
    void setUp() {
        myBmwCarDto = new MyBmwCarDto();
    }

    @Test
    void testGetAndSetId() {
        String id = "12345";
        myBmwCarDto.setId(id);
        assertEquals(id, myBmwCarDto.getId());
    }

    @Test
    void testGetAndSetBmwModel() {
        BmwModelsEnum bmwModel = BmwModelsEnum.series6;
        myBmwCarDto.setBmwModel(bmwModel);
        assertEquals(bmwModel, myBmwCarDto.getBmwModel());
    }

    @Test
    void testGetAndSetHorsePower() {
        int horsePower = 200;
        myBmwCarDto.setHorsePower(horsePower);
        assertEquals(horsePower, myBmwCarDto.getHorsePower());
    }

    @Test
    void testGetAndSetReleaseDate() {
        String releaseDate = "2020-01-01";
        myBmwCarDto.setReleaseDate(releaseDate);
        assertEquals(releaseDate, myBmwCarDto.getReleaseDate());
    }

    @Test
    void testGetAndSetCategory() {
        CategoryName category = CategoryName.Sedan;
        myBmwCarDto.setCategory(category);
        assertEquals(category, myBmwCarDto.getCategory());
    }

    @Test
    void testGetAndSetEngine() {
        EngineTypeEnum engine = EngineTypeEnum.Diesel;
        myBmwCarDto.setEngine(engine);
        assertEquals(engine, myBmwCarDto.getEngine());
    }

    @Test
    void testGetAndSetTransmission() {
        TransmissionType transmission = TransmissionType.Manual;
        myBmwCarDto.setTransmission(transmission);
        assertEquals(transmission, myBmwCarDto.getTransmission());
    }

    @Test
    void testGetAndSetKilometers() {
        int kilometers = 15000;
        myBmwCarDto.setKilometers(kilometers);
        assertEquals(kilometers, myBmwCarDto.getKilometers());
    }

    @Test
    void testGetAndSetBoughtFor() {
        double boughtFor = 35000.0;
        myBmwCarDto.setBoughtFor(boughtFor);
        assertEquals(boughtFor, myBmwCarDto.getBoughtFor());
    }

    @Test
    void testGetAndSetCurrencyName() {
        CurrencyName currencyName = CurrencyName.Euro;
        myBmwCarDto.setCurrencyName(currencyName);
        assertEquals(currencyName, myBmwCarDto.getCurrencyName());
    }

    @Test
    void testGetAndSetDescription() {
        String description = "A very nice BMW car.";
        myBmwCarDto.setDescription(description);
        assertEquals(description, myBmwCarDto.getDescription());
    }

    @Test
    void testGetAndSetImageUrl() {
        String imageUrl = "http://example.com/image.jpg";
        myBmwCarDto.setImageUrl(imageUrl);
        assertEquals(imageUrl, myBmwCarDto.getImageUrl());
    }
}
