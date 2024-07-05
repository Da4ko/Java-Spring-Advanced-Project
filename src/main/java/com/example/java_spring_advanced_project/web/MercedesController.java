package com.example.java_spring_advanced_project.web;

import com.example.java_spring_advanced_project.model.binding.BmwAddBindingModel;
import com.example.java_spring_advanced_project.model.binding.MercedesAddBindingModel;
import com.example.java_spring_advanced_project.model.dto.HomeMercedesCarsDto;
import com.example.java_spring_advanced_project.service.MercedesService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public ModelAndView addMercedesPage(Model model){
        if(!model.containsAttribute("mercedesAddBindingModel")){
            model.addAttribute("mercedesAddBindingModel", new MercedesAddBindingModel());
        }
        return new ModelAndView("add-mercedes");
    }
    @PostMapping("/add-mercedes")
    public ModelAndView create(@Valid MercedesAddBindingModel mercedesAddBindingModel,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("mercedesAddBindingModel", new MercedesAddBindingModel());
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.mercedesAddBindingModel", bindingResult);
            return new ModelAndView("add-mercedes");
        }

        boolean isCreated = mercedesService.createMercedes(mercedesAddBindingModel);
        String view = isCreated ? "redirect:/mercedes/mercedes-cars-home" : "add-mercedes";
        return new ModelAndView(view);
    }
}
