package com.example.java_spring_advanced_project.service.impl;

import com.example.java_spring_advanced_project.model.entity.PorscheModel;
import com.example.java_spring_advanced_project.model.entity.enums.PorscheModelsEnum;
import com.example.java_spring_advanced_project.repository.PorscheModelRepository;
import com.example.java_spring_advanced_project.service.PorscheModelService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class PorscheModelServiceImpl implements PorscheModelService {

    private final PorscheModelRepository porscheModelRepository;

    public PorscheModelServiceImpl(PorscheModelRepository porscheModelRepository) {
        this.porscheModelRepository = porscheModelRepository;
    }

    @Override
    public void initPorscheModels() {
        if (porscheModelRepository.count() == 0) {
            Arrays.stream(PorscheModelsEnum.values())
                    .forEach(porscheModelName -> {
                        PorscheModel porscheModel = new PorscheModel(porscheModelName, "Description for " + porscheModelName.name());
                        porscheModelRepository.save(porscheModel);
                    });
        }
    }
}