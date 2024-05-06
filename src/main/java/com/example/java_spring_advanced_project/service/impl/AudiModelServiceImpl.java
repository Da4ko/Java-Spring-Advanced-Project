package com.example.java_spring_advanced_project.service.impl;

import com.example.java_spring_advanced_project.model.entity.AudiModel;
import com.example.java_spring_advanced_project.model.entity.enums.AudiModelsEnum;
import com.example.java_spring_advanced_project.repository.AudiModelRepository;
import com.example.java_spring_advanced_project.service.AudiModelService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class AudiModelServiceImpl implements AudiModelService {

    private final AudiModelRepository audiModelRepository;

    public AudiModelServiceImpl(AudiModelRepository audiModelRepository) {
        this.audiModelRepository = audiModelRepository;
    }

    @Override
    public void initAudiModels() {

           if(audiModelRepository.count() == 0){
                Arrays.stream(AudiModelsEnum.values())
                        .forEach(audiModelName -> {
                            AudiModel audiModel = new AudiModel(audiModelName, "description for " + audiModelName.name());
                            audiModelRepository.save(audiModel);
                        });
            }
    }
}
