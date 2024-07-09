package com.example.java_spring_advanced_project.web;


import com.example.java_spring_advanced_project.model.binding.PorscheAddBindingModel;
import com.example.java_spring_advanced_project.model.dto.HomePorscheCarsDto;
import com.example.java_spring_advanced_project.service.PorscheService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public ModelAndView addPorschePage(Model model){
        if(!model.containsAttribute("porscheAddBindingModel")){
            model.addAttribute("porscheAddBindingModel", new PorscheAddBindingModel());
        }
        return new ModelAndView("add-porsche");
    }
    @PostMapping("/add-porsche")
    public ModelAndView create(@Valid PorscheAddBindingModel porscheAddBindingModel,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("porscheAddBindingModel", new PorscheAddBindingModel());
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.porscheAddBindingModel", bindingResult);
            return new ModelAndView("add-porsche");
        }

        boolean isCreated = porscheService.createPorsche(porscheAddBindingModel);
        String view = isCreated ? "redirect:/porsche/porsche-cars-home" : "add-porsche";
        return new ModelAndView(view);
    }
    @DeleteMapping("/{uuid}")
    public String delete(@PathVariable("uuid") String uuid, Model model){

        porscheService.deletePorsche(uuid);

        return "redirect:/porsche/porsche-cars-home";
    }
}
