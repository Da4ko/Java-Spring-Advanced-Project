package com.example.java_spring_advanced_project.web;

import com.example.java_spring_advanced_project.model.binding.AudiAddBindingModel;
import com.example.java_spring_advanced_project.model.binding.MercedesAddBindingModel;
import com.example.java_spring_advanced_project.model.dto.HomeMercedesCarsDto;
import com.example.java_spring_advanced_project.service.MercedesService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/mercedes")
public class MercedesController {

    private final MercedesService mercedesService;

    public MercedesController(MercedesService mercedesService) {
        this.mercedesService = mercedesService;
    }

    @GetMapping("/mercedes-cars-home")
    public ModelAndView mercedesHome() {
        ModelAndView modelAndView = new ModelAndView("mercedes.cars");
        HomeMercedesCarsDto homeMercedesCars = mercedesService.getMercedesCarsForHomePage();
        modelAndView.addObject("homeMercedesCarsDto", homeMercedesCars);

        return modelAndView;
    }

    @GetMapping("/add-mercedes")
    public ModelAndView addMercedesPage(){
        return new ModelAndView("add-mercedes");
    }
    @PostMapping("/add-mercedes")
    public ModelAndView create(MercedesAddBindingModel mercedesAddBindingModel){

        mercedesService.createMercedes(mercedesAddBindingModel);
        return new ModelAndView("redirect:/mercedes.cars");
    }
}
