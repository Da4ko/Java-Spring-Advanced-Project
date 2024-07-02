package com.example.java_spring_advanced_project.service.impl;

import com.example.java_spring_advanced_project.model.binding.BmwAddBindingModel;
import com.example.java_spring_advanced_project.model.dto.HomeBmwCarsDto;
import com.example.java_spring_advanced_project.model.dto.MyCars.MyBmwCarDto;
import com.example.java_spring_advanced_project.model.dto.OthersCars.OthersBmwCarDto;
import com.example.java_spring_advanced_project.model.entity.BmwCar;
import com.example.java_spring_advanced_project.repository.BmwCarsRepository;
import com.example.java_spring_advanced_project.service.BmwService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BmwServiceImpl implements BmwService {

    private final BmwCarsRepository bmwCarsRepository;

    public BmwServiceImpl(BmwCarsRepository bmwCarsRepository) {
        this.bmwCarsRepository = bmwCarsRepository;
    }

    @Override
    public HomeBmwCarsDto getBmwCarsForHomePage() {

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

        List<BmwCar> bmwCars = bmwCarsRepository.findAll();
        List<MyBmwCarDto> myBmwCars = new ArrayList<>();
        List<OthersBmwCarDto> othersBmwCars = new ArrayList<>();

        for (BmwCar bmwCar : bmwCars) {
            if (bmwCar.getOwner().getUsername().equals(currentUsername)) {
                myBmwCars.add(new MyBmwCarDto(bmwCar));
            } else {
                othersBmwCars.add(new OthersBmwCarDto(bmwCar));
            }
        }

        return new HomeBmwCarsDto(myBmwCars, othersBmwCars);
    }

    @Override
    public void createBmw(BmwAddBindingModel bmwAddBindingModel) {

    }
}
