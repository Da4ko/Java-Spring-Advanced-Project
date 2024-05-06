package com.example.java_spring_advanced_project.init;

import com.example.java_spring_advanced_project.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataBaseInitializer implements CommandLineRunner {

    private final AudiModelService audiModelService;
    private final BmwModelService bmwModelService;
    private final CategoryService categoryService;
    private final CurrencyService currencyService;
    private final EngineService engineService;
    private final MercedesModelService mercedesModelService;
    private final PorscheModelService porscheModelService;
    private final TransmissionService transmissionService;
    private final UserRoleService userRoleService;

    public DataBaseInitializer(AudiModelService audiModelService, BmwModelService bmwModelService, CategoryService categoryService,
                               CurrencyService currencyService, EngineService engineService, MercedesModelService mercedesModelService,
                               PorscheModelService porscheModelService, TransmissionService transmissionService, UserRoleService userRoleService) {
        this.audiModelService = audiModelService;
        this.bmwModelService = bmwModelService;
        this.categoryService = categoryService;
        this.currencyService = currencyService;
        this.engineService = engineService;
        this.mercedesModelService = mercedesModelService;
        this.porscheModelService = porscheModelService;
        this.transmissionService = transmissionService;
        this.userRoleService = userRoleService;
    }


    @Override
    public void run(String...args) throws Exception{
        audiModelService.initAudiModels();
        bmwModelService.initBmwModels();
        categoryService.initCategories();
        currencyService.initCurrencies();
        engineService.initEngines();
        mercedesModelService.initMercedesModels();
        porscheModelService.initPorscheModels();
        transmissionService.initTransmissions();
        userRoleService.initRoles();
    }
}
