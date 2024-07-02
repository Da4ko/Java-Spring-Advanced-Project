package com.example.java_spring_advanced_project.service;

import com.example.java_spring_advanced_project.model.binding.PorscheAddBindingModel;
import com.example.java_spring_advanced_project.model.dto.HomePorscheCarsDto;

public interface PorscheService {
    HomePorscheCarsDto getPorscheCarsForHomePage();

    void createPorsche(PorscheAddBindingModel porscheAddBindingModel);
}
