package com.example.java_spring_advanced_project.service.impl;

import com.example.java_spring_advanced_project.model.binding.MercedesAddBindingModel;
import com.example.java_spring_advanced_project.model.dto.HomeMercedesCarsDto;
import com.example.java_spring_advanced_project.model.dto.MyCars.MyMercedesCarDto;
import com.example.java_spring_advanced_project.model.dto.OthersCars.OthersMercedesCarDto;
import com.example.java_spring_advanced_project.model.entity.*;
import com.example.java_spring_advanced_project.repository.*;
import com.example.java_spring_advanced_project.service.MercedesService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class MercedesServiceImpl implements MercedesService {

    private final MercedesCarsRepository mercedesCarsRepository;
    private final MercedesModelRepository mercedesModelRepository;
    private final CategoryRepository categoryRepository;
    private final EngineRepository engineRepository;
    private final TransmissionRepository transmissionRepository;
    private final CurrencyRepository currencyRepository;
    private final UserRepository userRepository;

    public MercedesServiceImpl(MercedesCarsRepository mercedesCarsRepository, MercedesModelRepository mercedesModelRepository, CategoryRepository categoryRepository, EngineRepository engineRepository, TransmissionRepository transmissionRepository, CurrencyRepository currencyRepository, UserRepository userRepository) {
        this.mercedesCarsRepository = mercedesCarsRepository;
        this.mercedesModelRepository = mercedesModelRepository;
        this.categoryRepository = categoryRepository;
        this.engineRepository = engineRepository;
        this.transmissionRepository = transmissionRepository;
        this.currencyRepository = currencyRepository;
        this.userRepository = userRepository;
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
    public boolean createMercedes(MercedesAddBindingModel mercedesAddBindingModel) {
        MercedesCar mercedesCar = new MercedesCar();

        // Fetch necessary entities
        MercedesModel model = null;
        if (mercedesAddBindingModel.getMercedesModel() != null) {
            model = mercedesModelRepository.findByModel(mercedesAddBindingModel.getMercedesModel())
                    .orElseThrow(() -> new IllegalStateException("MercedesModel not found: " + mercedesAddBindingModel.getMercedesModel()));
        } else {
            throw new IllegalArgumentException("MercedesModel must be specified.");
        }

        Category category = null;
        if (mercedesAddBindingModel.getCategoryName() != null) {
            category = categoryRepository.findByCategory(mercedesAddBindingModel.getCategoryName())
                    .orElseThrow(() -> new IllegalStateException("Category not found: " + mercedesAddBindingModel.getCategoryName()));
        } else {
            throw new IllegalArgumentException("Category must be specified.");
        }

        Engine engine = null;
        if (mercedesAddBindingModel.getEngineType() != null) {
            engine = engineRepository.findByEngineType(mercedesAddBindingModel.getEngineType())
                    .orElseThrow(() -> new IllegalStateException("Engine not found for type: " + mercedesAddBindingModel.getEngineType()));
        } else {
            throw new IllegalArgumentException("Engine type must be specified.");
        }

        Transmission transmission = null;
        if (mercedesAddBindingModel.getTransmission() != null) {
            transmission = transmissionRepository.findByTransmission(mercedesAddBindingModel.getTransmission())
                    .orElseThrow(() -> new IllegalStateException("Transmission not found: " + mercedesAddBindingModel.getTransmission()));
        } else {
            throw new IllegalArgumentException("Transmission must be specified.");
        }

        Currency currency = null;
        if (mercedesAddBindingModel.getCurrencyName() != null) {
            currency = currencyRepository.findByCurrency(mercedesAddBindingModel.getCurrencyName())
                    .orElseThrow(() -> new IllegalStateException("Currency not found: " + mercedesAddBindingModel.getCurrencyName()));
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

                // Set properties on MercedesCar
                mercedesCar.setMercedesModel(model);
                mercedesCar.setHorsepowers(mercedesAddBindingModel.getHorsePower());
                mercedesCar.setImageUrl(mercedesAddBindingModel.getImageUrl());
                mercedesCar.setReleaseDate(mercedesAddBindingModel.getReleaseDate());
                mercedesCar.setCategory(category);
                mercedesCar.setEngine(engine);
                mercedesCar.setTransmission(transmission);
                mercedesCar.setKilometers(mercedesAddBindingModel.getKilometers());
                mercedesCar.setPrice(mercedesAddBindingModel.getPrice());
                mercedesCar.setCurrency(currency);
                mercedesCar.setDescription(mercedesAddBindingModel.getDescription());
                mercedesCar.setOwner(currentUser); // Set current user as owner

                try {
                    mercedesCarsRepository.save(mercedesCar);
                    return true; // Successfully created MercedesCar
                } catch (Exception e) {
                    e.printStackTrace();
                    return false; // Failed to save MercedesCar
                }
            } else {
                throw new IllegalStateException("Current user not found!");
            }
        } else {
            throw new IllegalStateException("Authentication not found or not authenticated!");
        }
    }
    @Override
    public void deleteMercedes(String mercedesUUID) {
        mercedesCarsRepository.deleteMercedesCarById(mercedesUUID);
    }
}
