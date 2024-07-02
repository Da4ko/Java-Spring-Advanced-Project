package com.example.java_spring_advanced_project.web;

import com.example.java_spring_advanced_project.model.binding.AudiAddBindingModel;
import com.example.java_spring_advanced_project.model.binding.PorscheAddBindingModel;
import com.example.java_spring_advanced_project.model.dto.HomePorscheCarsDto;
import com.example.java_spring_advanced_project.service.PorscheService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/porsche")
public class PorscheController {

    private final PorscheService porscheService;

    public PorscheController(PorscheService porscheService) {
        this.porscheService = porscheService;
    }

    @GetMapping("/porsche-cars-home")
    public ModelAndView porscheHome() {
        ModelAndView modelAndView = new ModelAndView("porsche-cars");
        HomePorscheCarsDto homePorscheCars = porscheService.getPorscheCarsForHomePage();
        modelAndView.addObject("homePorscheCarsDto", homePorscheCars);

        return modelAndView;
    }

    @GetMapping("/add-porsche")
    public ModelAndView addPorschePage(){
        return new ModelAndView("add-porsche");
    }
    @PostMapping("/add-porsche")
    public ModelAndView create(PorscheAddBindingModel porscheAddBindingModel){

        porscheService.createPorsche(porscheAddBindingModel);
        return new ModelAndView("redirect:/porsche-cars");
    }
}
