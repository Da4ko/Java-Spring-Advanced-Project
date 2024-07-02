package com.example.java_spring_advanced_project.web;

import com.example.java_spring_advanced_project.model.binding.AudiAddBindingModel;
import com.example.java_spring_advanced_project.model.dto.HomeAudiCarsDto;
import com.example.java_spring_advanced_project.service.AudiService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/audi")
public class AudiController {

    private final AudiService audiService;

    public AudiController(AudiService audiService) {
        this.audiService = audiService;
    }

    @GetMapping("/audi-cars-home")
    public ModelAndView audiHome() {
        ModelAndView modelAndView = new ModelAndView("audi-cars");
        HomeAudiCarsDto homeAudiCars = audiService.getAudiCarsForHomePage();
        modelAndView.addObject("homeAudiCarsDto", homeAudiCars);
        return modelAndView;
    }

    @GetMapping("/add-audi")
    public ModelAndView addAudiPage() {
        return new ModelAndView("add-audi");
    }

    @PostMapping("/add-audi")
    public ModelAndView create(AudiAddBindingModel audiAddBindingModel) {
        boolean isCreated = audiService.createAudi(audiAddBindingModel);
        String view = isCreated ? "redirect:/audi/audi-cars-home" : "add-audi";
        return new ModelAndView(view);
    }
}
