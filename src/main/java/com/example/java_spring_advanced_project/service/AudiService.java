package com.example.java_spring_advanced_project.service;

import com.example.java_spring_advanced_project.model.binding.AudiAddBindingModel;
import com.example.java_spring_advanced_project.model.dto.HomeAudiCarsDto;

import java.util.UUID;

public interface AudiService {
    HomeAudiCarsDto getAudiCarsForHomePage();

    boolean createAudi(AudiAddBindingModel audiAddBindingModel);

    void deleteAudi(String audiUUID);
}
