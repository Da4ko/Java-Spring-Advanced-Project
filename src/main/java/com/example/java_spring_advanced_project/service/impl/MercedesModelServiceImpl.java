package com.example.java_spring_advanced_project.service.impl;

import com.example.java_spring_advanced_project.model.entity.MercedesModel;
import com.example.java_spring_advanced_project.model.entity.enums.MercedesModelsEnum;
import com.example.java_spring_advanced_project.repository.MercedesModelRepository;
import com.example.java_spring_advanced_project.service.MercedesModelService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class MercedesModelServiceImpl implements MercedesModelService {

    private final MercedesModelRepository mercedesModelRepository;

    public MercedesModelServiceImpl(MercedesModelRepository mercedesModelRepository) {
        this.mercedesModelRepository = mercedesModelRepository;
    }

    @Override
    public void initMercedesModels() {
        if (mercedesModelRepository.count() == 0) {
            Arrays.stream(MercedesModelsEnum.values())
                    .forEach(mercedesModelName -> {
                        MercedesModel mercedesModel = new MercedesModel(mercedesModelName, "Description for " + mercedesModelName.name());
                        mercedesModelRepository.save(mercedesModel);
                    });
        }
    }
}