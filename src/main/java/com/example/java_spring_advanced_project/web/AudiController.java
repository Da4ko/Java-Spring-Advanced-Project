package com.example.java_spring_advanced_project.web;

import com.example.java_spring_advanced_project.model.binding.AudiAddBindingModel;
import com.example.java_spring_advanced_project.model.dto.HomeAudiCarsDto;
import com.example.java_spring_advanced_project.service.AudiService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

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
    public ModelAndView addAudiPage(Model model) {
        if(!model.containsAttribute("audiAddBindingModel")){
            model.addAttribute("audiAddBindingModel", new AudiAddBindingModel());
        }
        return new ModelAndView("add-audi");
    }

    @PostMapping("/add-audi")
    public ModelAndView create(@Valid AudiAddBindingModel audiAddBindingModel,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("audiAddBindingModel", new AudiAddBindingModel());
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.audiAddBindingModel", bindingResult);
            return new ModelAndView("add-audi");
        }
        boolean isCreated = audiService.createAudi(audiAddBindingModel);
        String view = isCreated ? "redirect:/audi/audi-cars-home" : "add-audi";
        return new ModelAndView(view);
    }

    @DeleteMapping("/{uuid}")
    public String delete(@PathVariable("uuid") String uuid, Model model){

        audiService.deleteAudi(uuid);

        return "redirect:/audi/audi-cars-home";
    }
}
