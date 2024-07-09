package com.example.java_spring_advanced_project.service.impl;

import com.example.java_spring_advanced_project.model.binding.AudiAddBindingModel;
import com.example.java_spring_advanced_project.model.dto.HomeAudiCarsDto;
import com.example.java_spring_advanced_project.model.dto.MyCars.MyAudiCarDto;
import com.example.java_spring_advanced_project.model.dto.OthersCars.OthersAudiCarDto;
import com.example.java_spring_advanced_project.model.entity.*;
import com.example.java_spring_advanced_project.repository.*;
import com.example.java_spring_advanced_project.service.AudiService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.example.java_spring_advanced_project.model.entity.Currency;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AudiServiceImpl implements AudiService {

    private final AudiCarsRepository audiCarsRepository;

    private final AudiModelRepository audiModelRepository;
    private final CategoryRepository categoryRepository;
    private final EngineRepository engineRepository;
    private final TransmissionRepository transmissionRepository;
    private final CurrencyRepository currencyRepository;

    private final UserRepository userRepository;




    public AudiServiceImpl(AudiCarsRepository audiCarsRepository, AudiModelRepository audiModelRepository, CategoryRepository categoryRepository, EngineRepository engineRepository, TransmissionRepository transmissionRepository, CurrencyRepository currencyRepository, UserRepository userRepository) {
        this.audiCarsRepository = audiCarsRepository;
        this.audiModelRepository = audiModelRepository;
        this.categoryRepository = categoryRepository;
        this.engineRepository = engineRepository;
        this.transmissionRepository = transmissionRepository;
        this.currencyRepository = currencyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public HomeAudiCarsDto getAudiCarsForHomePage() {

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

        List<AudiCar> audiCars = audiCarsRepository.findAll();
        List<MyAudiCarDto> myAudiCars = new ArrayList<>();
        List<OthersAudiCarDto> othersAudiCars = new ArrayList<>();

        for (AudiCar audiCar : audiCars) {
            if (audiCar.getOwner().getUsername().equals(currentUsername)) {
                myAudiCars.add(new MyAudiCarDto(audiCar));
            } else {
                othersAudiCars.add(new OthersAudiCarDto(audiCar));
            }
        }

        return new HomeAudiCarsDto(myAudiCars, othersAudiCars);
    }

    @Override
    public boolean createAudi(AudiAddBindingModel audiAddBindingModel) {
        AudiCar audiCar = new AudiCar();

        // Fetch necessary entities
        AudiModel model = null;
        if (audiAddBindingModel.getAudiModel() != null) {
            model = audiModelRepository.findByModel(audiAddBindingModel.getAudiModel())
                    .orElseThrow(() -> new IllegalStateException("AudiModel not found: " + audiAddBindingModel.getAudiModel()));
        } else {
            throw new IllegalArgumentException("AudiModel must be specified.");
        }

        Category category = null;
        if (audiAddBindingModel.getCategoryName() != null) {
            category = categoryRepository.findByCategory(audiAddBindingModel.getCategoryName())
                    .orElseThrow(() -> new IllegalStateException("Category not found: " + audiAddBindingModel.getCategoryName()));
        } else {
            throw new IllegalArgumentException("Category must be specified.");
        }

        Engine engine = null;
        if (audiAddBindingModel.getEngineType() != null) {
            engine = engineRepository.findByEngineType(audiAddBindingModel.getEngineType())
                    .orElseThrow(() -> new IllegalStateException("Engine not found for type: " + audiAddBindingModel.getEngineType()));
        } else {
            throw new IllegalArgumentException("Engine type must be specified.");
        }

        Transmission transmission = null;
        if (audiAddBindingModel.getTransmission() != null) {
            transmission = transmissionRepository.findByTransmission(audiAddBindingModel.getTransmission())
                    .orElseThrow(() -> new IllegalStateException("Transmission not found: " + audiAddBindingModel.getTransmission()));
        } else {
            throw new IllegalArgumentException("Transmission must be specified.");
        }

        Currency currency = null;
        if (audiAddBindingModel.getCurrencyName() != null) {
            currency = currencyRepository.findByCurrency(audiAddBindingModel.getCurrencyName())
                    .orElseThrow(() -> new IllegalStateException("Currency not found: " + audiAddBindingModel.getCurrencyName()));
        } else {
            throw new IllegalArgumentException("Currency must be specified.");
        }

        // Fetch current user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = null;
        UserEntity currentUser = null;

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                currentUsername = ((UserDetails) principal).getUsername();
            } else {
                currentUsername = principal.toString();
            }

            Optional<UserEntity> userOptional = userRepository.findByUsername(currentUsername);
            if (userOptional.isPresent()) {
                currentUser = userOptional.get();

                // Set properties on AudiCar
                audiCar.setAudiModel(model);
                audiCar.setHorsepowers(audiAddBindingModel.getHorsePower());
                audiCar.setImageUrl(audiAddBindingModel.getImageUrl());
                audiCar.setReleaseDate(audiAddBindingModel.getReleaseDate());
                audiCar.setCategory(category);
                audiCar.setEngine(engine);
                audiCar.setTransmission(transmission);
                audiCar.setKilometers(audiAddBindingModel.getKilometers());
                audiCar.setPrice(audiAddBindingModel.getPrice());
                audiCar.setCurrency(currency);
                audiCar.setDescription(audiAddBindingModel.getDescription());
                audiCar.setOwner(currentUser); // Set current user as owner

                try {
                    audiCarsRepository.save(audiCar);
                    return true; // Successfully created AudiCar
                } catch (Exception e) {
                    e.printStackTrace();
                    return false; // Failed to save AudiCar
                }
            } else {
                throw new IllegalStateException("Current user not found!");
            }
        } else {
            throw new IllegalStateException("Authentication not found or not authenticated!");
        }
    }

    @Override
    public void deleteAudi(String audiUUID) {
        audiCarsRepository.deleteAudiCarById(audiUUID);
    }


}
