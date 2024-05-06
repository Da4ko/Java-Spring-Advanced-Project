package com.example.java_spring_advanced_project.service.impl;

import com.example.java_spring_advanced_project.model.entity.BmwModel;
import com.example.java_spring_advanced_project.model.entity.enums.BmwModelsEnum;
import com.example.java_spring_advanced_project.repository.BmwModelRepository;
import com.example.java_spring_advanced_project.service.BmwModelService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class BmwModelServiceImpl implements BmwModelService {

    private final BmwModelRepository bmwModelRepository;

    public BmwModelServiceImpl(BmwModelRepository bmwModelRepository) {
        this.bmwModelRepository = bmwModelRepository;
    }

    @Override
    public void initBmwModels() {
        if (bmwModelRepository.count() == 0) {
            Arrays.stream(BmwModelsEnum.values())
                    .forEach(bmwModelName -> {
                        BmwModel bmwModel = new BmwModel(bmwModelName, "description for " + bmwModelName.name());
                        bmwModelRepository.save(bmwModel);
                    });
        }
    }
}