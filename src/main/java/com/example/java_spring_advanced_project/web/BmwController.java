package com.example.java_spring_advanced_project.web;

import com.example.java_spring_advanced_project.model.binding.BmwAddBindingModel;
import com.example.java_spring_advanced_project.model.dto.HomeBmwCarsDto;
import com.example.java_spring_advanced_project.service.BmwService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/bmw")
public class BmwController {

    private final BmwService bmwService;

    public BmwController(BmwService bmwService) {
        this.bmwService = bmwService;
    }

    @GetMapping("/bmw-cars-home")
    public ModelAndView bmwHome() {
        ModelAndView modelAndView = new ModelAndView("BMW-cars");
        HomeBmwCarsDto homeBmwCars = bmwService.getBmwCarsForHomePage();
        modelAndView.addObject("homeBmwCarsDto", homeBmwCars);

        return modelAndView;
    }

    @GetMapping("/add-bmw")
    public ModelAndView addBmwPage(){
        return new ModelAndView("add-bmw");
    }
    @PostMapping("/add-bmw")
    public ModelAndView create(BmwAddBindingModel bmwAddBindingModel){
        boolean isCreated = bmwService.createBmw(bmwAddBindingModel);
        String view = isCreated ? "redirect:/bmw/bmw-cars-home" : "add-bmw";
        return new ModelAndView(view);
    }
}
