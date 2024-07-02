package com.example.java_spring_advanced_project.service.impl;

import com.example.java_spring_advanced_project.model.binding.PorscheAddBindingModel;
import com.example.java_spring_advanced_project.model.dto.HomePorscheCarsDto;
import com.example.java_spring_advanced_project.model.dto.MyCars.MyPorscheCarDto;
import com.example.java_spring_advanced_project.model.dto.OthersCars.OthersPorscheCarDto;
import com.example.java_spring_advanced_project.model.entity.PorscheCar;
import com.example.java_spring_advanced_project.repository.PorscheCarsRepository;
import com.example.java_spring_advanced_project.service.PorscheService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PorscheServiceImpl implements PorscheService {

    private final PorscheCarsRepository porscheCarsRepository;

    public PorscheServiceImpl(PorscheCarsRepository porscheCarsRepository) {
        this.porscheCarsRepository = porscheCarsRepository;
    }

    @Override
    public HomePorscheCarsDto getPorscheCarsForHomePage() {

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

        List<PorscheCar> porscheCars = porscheCarsRepository.findAll();
        List<MyPorscheCarDto> myPorscheCars = new ArrayList<>();
        List<OthersPorscheCarDto> othersPorscheCars = new ArrayList<>();

        for (PorscheCar porscheCar : porscheCars) {
            if (porscheCar.getOwner().getUsername().equals(currentUsername)) {
                myPorscheCars.add(new MyPorscheCarDto(porscheCar));
            } else {
                othersPorscheCars.add(new OthersPorscheCarDto(porscheCar));
            }
        }

        return new HomePorscheCarsDto(myPorscheCars, othersPorscheCars);
    }

    @Override
    public void createPorsche(PorscheAddBindingModel porscheAddBindingModel) {

    }
}
