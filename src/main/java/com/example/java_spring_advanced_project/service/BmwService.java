package com.example.java_spring_advanced_project.service;

import com.example.java_spring_advanced_project.model.binding.BmwAddBindingModel;
import com.example.java_spring_advanced_project.model.dto.HomeBmwCarsDto;

public interface BmwService {
    HomeBmwCarsDto getBmwCarsForHomePage();

    void createBmw(BmwAddBindingModel bmwAddBindingModel);
}
