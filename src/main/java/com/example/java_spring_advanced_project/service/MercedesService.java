package com.example.java_spring_advanced_project.service;

import com.example.java_spring_advanced_project.model.binding.MercedesAddBindingModel;
import com.example.java_spring_advanced_project.model.dto.HomeMercedesCarsDto;

public interface MercedesService {
    HomeMercedesCarsDto getMercedesCarsForHomePage();

    boolean createMercedes(MercedesAddBindingModel mercedesAddBindingModel);
}
