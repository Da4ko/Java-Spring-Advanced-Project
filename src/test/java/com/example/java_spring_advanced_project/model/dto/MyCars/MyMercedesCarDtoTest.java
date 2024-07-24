package com.example.java_spring_advanced_project.model.dto.MyCars;

import com.example.java_spring_advanced_project.model.entity.enums.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyMercedesCarDtoTest {

    private MyMercedesCarDto myMercedesCarDto;

    @BeforeEach
    void setUp() {
        myMercedesCarDto = new MyMercedesCarDto();
    }

    @Test
    void testGetAndSetId() {
        String id = "12345";
        myMercedesCarDto.setId(id);
        assertEquals(id, myMercedesCarDto.getId());
    }

    @Test
    void testGetAndSetMercedesModel() {
        MercedesModelsEnum mercedesModel = MercedesModelsEnum.CL;
        myMercedesCarDto.setMercedesModel(mercedesModel);
        assertEquals(mercedesModel, myMercedesCarDto.getMercedesModel());
    }

    @Test
    void testGetAndSetHorsePower() {
        int horsePower = 250;
        myMercedesCarDto.setHorsePower(horsePower);
        assertEquals(horsePower, myMercedesCarDto.getHorsePower());
    }

    @Test
    void testGetAndSetReleaseDate() {
        String releaseDate = "2020-01-01";
        myMercedesCarDto.setReleaseDate(releaseDate);
        assertEquals(releaseDate, myMercedesCarDto.getReleaseDate());
    }

    @Test
    void testGetAndSetCategory() {
        CategoryName category = CategoryName.Sedan;
        myMercedesCarDto.setCategory(category);
        assertEquals(category, myMercedesCarDto.getCategory());
    }

    @Test
    void testGetAndSetEngine() {
        EngineTypeEnum engine = EngineTypeEnum.Gasoline;
        myMercedesCarDto.setEngine(engine);
        assertEquals(engine, myMercedesCarDto.getEngine());
    }

    @Test
    void testGetAndSetTransmission() {
        TransmissionType transmission = TransmissionType.Automatic;
        myMercedesCarDto.setTransmission(transmission);
        assertEquals(transmission, myMercedesCarDto.getTransmission());
    }

    @Test
    void testGetAndSetKilometers() {
        int kilometers = 20000;
        myMercedesCarDto.setKilometers(kilometers);
        assertEquals(kilometers, myMercedesCarDto.getKilometers());
    }

    @Test
    void testGetAndSetBoughtFor() {
        double boughtFor = 45000.0;
        myMercedesCarDto.setBoughtFor(boughtFor);
        assertEquals(boughtFor, myMercedesCarDto.getBoughtFor());
    }

    @Test
    void testGetAndSetCurrencyName() {
        CurrencyName currencyName = CurrencyName.Euro;
        myMercedesCarDto.setCurrencyName(currencyName);
        assertEquals(currencyName, myMercedesCarDto.getCurrencyName());
    }

    @Test
    void testGetAndSetDescription() {
        String description = "A luxury Mercedes car.";
        myMercedesCarDto.setDescription(description);
        assertEquals(description, myMercedesCarDto.getDescription());
    }

    @Test
    void testGetAndSetImageUrl() {
        String imageUrl = "http://example.com/image.jpg";
        myMercedesCarDto.setImageUrl(imageUrl);
        assertEquals(imageUrl, myMercedesCarDto.getImageUrl());
    }
}
