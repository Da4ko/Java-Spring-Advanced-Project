package com.example.java_spring_advanced_project.service.impl;

import com.example.java_spring_advanced_project.model.binding.PorscheAddBindingModel;
import com.example.java_spring_advanced_project.model.dto.HomePorscheCarsDto;
import com.example.java_spring_advanced_project.model.dto.MyCars.MyPorscheCarDto;
import com.example.java_spring_advanced_project.model.dto.OthersCars.OthersPorscheCarDto;
import com.example.java_spring_advanced_project.model.entity.*;
import com.example.java_spring_advanced_project.repository.*;
import com.example.java_spring_advanced_project.service.PorscheService;
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
public class PorscheServiceImpl implements PorscheService {

    private final PorscheModelRepository porscheModelRepository;
    private final CategoryRepository categoryRepository;
    private final EngineRepository engineRepository;
    private final TransmissionRepository transmissionRepository;
    private final CurrencyRepository currencyRepository;
    private final PorscheCarsRepository porscheCarsRepository;
    private final UserRepository userRepository;

    public PorscheServiceImpl(PorscheModelRepository porscheModelRepository, CategoryRepository categoryRepository,
                              EngineRepository engineRepository, TransmissionRepository transmissionRepository,
                              CurrencyRepository currencyRepository, PorscheCarsRepository porscheCarsRepository,
                              UserRepository userRepository) {
        this.porscheModelRepository = porscheModelRepository;
        this.categoryRepository = categoryRepository;
        this.engineRepository = engineRepository;
        this.transmissionRepository = transmissionRepository;
        this.currencyRepository = currencyRepository;
        this.porscheCarsRepository = porscheCarsRepository;
        this.userRepository = userRepository;
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
    public boolean createPorsche(PorscheAddBindingModel porscheAddBindingModel) {
        PorscheCar porscheCar = new PorscheCar();

        // Fetch necessary entities
        PorscheModel model = null;
        if (porscheAddBindingModel.getPorscheModel() != null) {
            model = porscheModelRepository.findByModel(porscheAddBindingModel.getPorscheModel())
                    .orElseThrow(() -> new IllegalStateException("PorscheModel not found: " + porscheAddBindingModel.getPorscheModel()));
        } else {
            throw new IllegalArgumentException("PorscheModel must be specified.");
        }

        Category category = null;
        if (porscheAddBindingModel.getCategoryName() != null) {
            category = categoryRepository.findByCategory(porscheAddBindingModel.getCategoryName())
                    .orElseThrow(() -> new IllegalStateException("Category not found: " + porscheAddBindingModel.getCategoryName()));
        } else {
            throw new IllegalArgumentException("Category must be specified.");
        }

        Engine engine = null;
        if (porscheAddBindingModel.getEngineType() != null) {
            engine = engineRepository.findByEngineType(porscheAddBindingModel.getEngineType())
                    .orElseThrow(() -> new IllegalStateException("Engine not found for type: " + porscheAddBindingModel.getEngineType()));
        } else {
            throw new IllegalArgumentException("Engine type must be specified.");
        }

        Transmission transmission = null;
        if (porscheAddBindingModel.getTransmission() != null) {
            transmission = transmissionRepository.findByTransmission(porscheAddBindingModel.getTransmission())
                    .orElseThrow(() -> new IllegalStateException("Transmission not found: " + porscheAddBindingModel.getTransmission()));
        } else {
            throw new IllegalArgumentException("Transmission must be specified.");
        }

        Currency currency = null;
        if (porscheAddBindingModel.getCurrencyName() != null) {
            currency = currencyRepository.findByCurrency(porscheAddBindingModel.getCurrencyName())
                    .orElseThrow(() -> new IllegalStateException("Currency not found: " + porscheAddBindingModel.getCurrencyName()));
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

                // Set properties on PorscheCar
                porscheCar.setPorscheModel(model);
                porscheCar.setHorsepowers(porscheAddBindingModel.getHorsePower());
                porscheCar.setImageUrl(porscheAddBindingModel.getImageUrl());
                porscheCar.setReleaseDate(porscheAddBindingModel.getReleaseDate());
                porscheCar.setCategory(category);
                porscheCar.setEngine(engine);
                porscheCar.setTransmission(transmission);
                porscheCar.setKilometers(porscheAddBindingModel.getKilometers());
                porscheCar.setPrice(porscheAddBindingModel.getPrice());
                porscheCar.setCurrency(currency);
                porscheCar.setDescription(porscheAddBindingModel.getDescription());
                porscheCar.setOwner(currentUser); // Set current user as owner

                try {
                    porscheCarsRepository.save(porscheCar);
                    return true; // Successfully created PorscheCar
                } catch (Exception e) {
                    e.printStackTrace();
                    return false; // Failed to save PorscheCar
                }
            } else {
                throw new IllegalStateException("Current user not found!");
            }
        } else {
            throw new IllegalStateException("Authentication not found or not authenticated!");
        }
    }
    @Override
    public void deletePorsche(String porscheUUID) {
        porscheCarsRepository.deletePorscheCarById(porscheUUID);
    }
}
