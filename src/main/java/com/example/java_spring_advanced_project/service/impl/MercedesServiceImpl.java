package com.example.java_spring_advanced_project.service.impl;

import com.example.java_spring_advanced_project.model.binding.MercedesAddBindingModel;
import com.example.java_spring_advanced_project.model.dto.HomeMercedesCarsDto;
import com.example.java_spring_advanced_project.model.dto.MyCars.MyMercedesCarDto;
import com.example.java_spring_advanced_project.model.dto.OthersCars.OthersMercedesCarDto;
import com.example.java_spring_advanced_project.model.entity.MercedesCar;
import com.example.java_spring_advanced_project.repository.MercedesCarsRepository;
import com.example.java_spring_advanced_project.service.MercedesService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MercedesServiceImpl implements MercedesService {

    private final MercedesCarsRepository mercedesCarsRepository;

    public MercedesServiceImpl(MercedesCarsRepository mercedesCarsRepository) {
        this.mercedesCarsRepository = mercedesCarsRepository;
    }

    @Override
    public HomeMercedesCarsDto getMercedesCarsForHomePage() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = null;

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                currentUsername = ((UserDetails) principal).getUsername();
            } else {
                currentUsername = principal.toString();
            }
        }

        List<MercedesCar> mercedesCars = mercedesCarsRepository.findAll();
        List<MyMercedesCarDto> myMercedesCars = new ArrayList<>();
        List<OthersMercedesCarDto> othersMercedesCars = new ArrayList<>();

        for (MercedesCar mercedesCar : mercedesCars) {
            if (mercedesCar.getOwner().getUsername().equals(currentUsername)) {
                myMercedesCars.add(new MyMercedesCarDto(mercedesCar));
            } else {
                othersMercedesCars.add(new OthersMercedesCarDto(mercedesCar));
            }
        }

        return new HomeMercedesCarsDto(myMercedesCars, othersMercedesCars);
    }

    @Override
    public void createMercedes(MercedesAddBindingModel mercedesAddBindingModel) {

    }
}
