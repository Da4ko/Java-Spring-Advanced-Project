package com.example.java_spring_advanced_project.service.impl;

import com.example.java_spring_advanced_project.model.binding.BmwAddBindingModel;
import com.example.java_spring_advanced_project.model.dto.HomeBmwCarsDto;
import com.example.java_spring_advanced_project.model.dto.MyCars.MyBmwCarDto;
import com.example.java_spring_advanced_project.model.dto.OthersCars.OthersBmwCarDto;
import com.example.java_spring_advanced_project.model.entity.*;
import com.example.java_spring_advanced_project.repository.*;
import com.example.java_spring_advanced_project.service.BmwService;
import com.example.java_spring_advanced_project.model.entity.Currency;
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
public class BmwServiceImpl implements BmwService {

    private final BmwCarsRepository bmwCarsRepository;
    private final BmwModelRepository bmwModelRepository;
    private final CategoryRepository categoryRepository;
    private final EngineRepository engineRepository;
    private final TransmissionRepository transmissionRepository;
    private final CurrencyRepository currencyRepository;
    private final UserRepository userRepository;

    public BmwServiceImpl(BmwCarsRepository bmwCarsRepository, BmwModelRepository bmwModelRepository, CategoryRepository categoryRepository, EngineRepository engineRepository, TransmissionRepository transmissionRepository, CurrencyRepository currencyRepository, UserRepository userRepository) {
        this.bmwCarsRepository = bmwCarsRepository;
        this.bmwModelRepository = bmwModelRepository;
        this.categoryRepository = categoryRepository;
        this.engineRepository = engineRepository;
        this.transmissionRepository = transmissionRepository;
        this.currencyRepository = currencyRepository;
        this.userRepository = userRepository;
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

    public boolean createBmw(BmwAddBindingModel bmwAddBindingModel) {
        BmwCar bmwCar = new BmwCar();

        // Fetch necessary entities
        BmwModel model = null;
        if (bmwAddBindingModel.getBmwModel() != null) {
            model =  bmwModelRepository.findByModel(bmwAddBindingModel.getBmwModel())
                    .orElseThrow(() -> new IllegalStateException("BmwModel not found: " + bmwAddBindingModel.getBmwModel()));
        } else {
            throw new IllegalArgumentException("BmwModel must be specified.");
        }

        Category category = null;
        if (bmwAddBindingModel.getCategoryName() != null) {
            category = categoryRepository.findByCategory(bmwAddBindingModel.getCategoryName())
                    .orElseThrow(() -> new IllegalStateException("Category not found: " + bmwAddBindingModel.getCategoryName()));
        } else {
            throw new IllegalArgumentException("Category must be specified.");
        }

        Engine engine = null;
        if (bmwAddBindingModel.getEngineType() != null) {
            engine = engineRepository.findByEngineType(bmwAddBindingModel.getEngineType())
                    .orElseThrow(() -> new IllegalStateException("Engine not found for type: " + bmwAddBindingModel.getEngineType()));
        } else {
            throw new IllegalArgumentException("Engine type must be specified.");
        }

        Transmission transmission = null;
        if (bmwAddBindingModel.getTransmission() != null) {
            transmission = transmissionRepository.findByTransmission(bmwAddBindingModel.getTransmission())
                    .orElseThrow(() -> new IllegalStateException("Transmission not found: " + bmwAddBindingModel.getTransmission()));
        } else {
            throw new IllegalArgumentException("Transmission must be specified.");
        }

        Currency currency = null;
        if (bmwAddBindingModel.getCurrencyName() != null) {
            currency = currencyRepository.findByCurrency(bmwAddBindingModel.getCurrencyName())
                    .orElseThrow(() -> new IllegalStateException("Currency not found: " + bmwAddBindingModel.getCurrencyName()));
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

                // Set properties on BmwCar
                bmwCar.setBmwModel(model);
                bmwCar.setHorsepowers(bmwAddBindingModel.getHorsePower());
                bmwCar.setImageUrl(bmwAddBindingModel.getImageUrl());
                bmwCar.setReleaseDate(bmwAddBindingModel.getReleaseDate());
                bmwCar.setCategory(category);
                bmwCar.setEngine(engine);
                bmwCar.setTransmission(transmission);
                bmwCar.setKilometers(bmwAddBindingModel.getKilometers());
                bmwCar.setPrice(bmwAddBindingModel.getPrice());
                bmwCar.setCurrency(currency);
                bmwCar.setDescription(bmwAddBindingModel.getDescription());
                bmwCar.setOwner(currentUser); // Set current user as owner

                try {
                    bmwCarsRepository.save(bmwCar);
                    return true; // Successfully created BmwCar
                } catch (Exception e) {
                    e.printStackTrace();
                    return false; // Failed to save BmwCar
                }
            } else {
                throw new IllegalStateException("Current user not found!");
            }
        } else {
            throw new IllegalStateException("Authentication not found or not authenticated!");
        }
    }

    @Override
    public void deleteBmw(String bmwUUID) {
        bmwCarsRepository.deleteBmwCarById(bmwUUID);
    }
}
