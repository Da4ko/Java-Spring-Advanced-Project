package com.example.java_spring_advanced_project.service.impl;

import com.example.java_spring_advanced_project.model.entity.Engine;
import com.example.java_spring_advanced_project.model.entity.enums.EngineTypeEnum;
import com.example.java_spring_advanced_project.repository.EngineRepository;
import com.example.java_spring_advanced_project.service.EngineService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class EngineServiceImpl implements EngineService {

    private final EngineRepository engineRepository;

    public EngineServiceImpl(EngineRepository engineRepository) {
        this.engineRepository = engineRepository;
    }

    @Override
    public void initEngines() {
        if (engineRepository.count() == 0) {
            Arrays.stream(EngineTypeEnum.values())
                    .forEach(engineType -> {
                        Engine engine = new Engine(engineType, "Description for " + engineType.name());
                        engineRepository.save(engine);
                    });
        }
    }
}