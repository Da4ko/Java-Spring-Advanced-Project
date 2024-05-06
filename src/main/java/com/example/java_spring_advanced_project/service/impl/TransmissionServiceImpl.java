package com.example.java_spring_advanced_project.service.impl;

import com.example.java_spring_advanced_project.model.entity.Transmission;
import com.example.java_spring_advanced_project.model.entity.enums.TransmissionType;
import com.example.java_spring_advanced_project.repository.TransmissionRepository;
import com.example.java_spring_advanced_project.service.TransmissionService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class TransmissionServiceImpl implements TransmissionService {

    private final TransmissionRepository transmissionRepository;

    public TransmissionServiceImpl(TransmissionRepository transmissionRepository) {
        this.transmissionRepository = transmissionRepository;
    }

    @Override
    public void initTransmissions() {
        if (transmissionRepository.count() == 0) {
            Arrays.stream(TransmissionType.values())
                    .forEach(transmissionType -> {
                        Transmission transmission = new Transmission(transmissionType, "description for" + transmissionType.name());
                        transmissionRepository.save(transmission);
                    });
        }
    }
}