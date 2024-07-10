package com.example.java_spring_advanced_project.web;


import com.example.java_spring_advanced_project.model.binding.BmwAddBindingModel;
import com.example.java_spring_advanced_project.model.dto.HomeBmwCarsDto;
import com.example.java_spring_advanced_project.service.BmwService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public ModelAndView addBmwPage(Model model){
        if(!model.containsAttribute("bmwAddBindingModel")){
            model.addAttribute("bmwAddBindingModel", new BmwAddBindingModel());
        }
        return new ModelAndView("add-bmw");
    }
    @PostMapping("/add-bmw")
    public ModelAndView create(@Valid BmwAddBindingModel bmwAddBindingModel,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("bmwAddBindingModel", new BmwAddBindingModel());
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.bmwAddBindingModel", bindingResult);
            return new ModelAndView("add-bmw");
        }


        boolean isCreated = bmwService.createBmw(bmwAddBindingModel);
        String view = isCreated ? "redirect:/bmw/bmw-cars-home" : "add-bmw";
        return new ModelAndView(view);
    }

    @DeleteMapping("/{uuid}")
    public String delete(@PathVariable("uuid") String uuid, Model model){

        bmwService.deleteBmw(uuid);

        return "redirect:/bmw/bmw-cars-home";
    }
}
