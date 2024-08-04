package com.example.java_spring_advanced_project.web;


import com.example.java_spring_advanced_project.model.binding.ReportABugBindingModel;
import com.example.java_spring_advanced_project.model.dto.HomeBugsDto;
import com.example.java_spring_advanced_project.service.BugsService;
import jakarta.validation.Valid;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/bugs")
public class BugController {


    private final BugsService bugsService;



    public BugController(BugsService bugsService) {
        this.bugsService = bugsService;

    }
    @GetMapping("/reported-bugs")
    public ModelAndView reportedBugsHome() {

        ModelAndView modelAndView = new ModelAndView("bugs");
        HomeBugsDto bugsToBeVisualised = bugsService.getBugsForBugsHome();
        modelAndView.addObject("homeBugsDto", bugsToBeVisualised);

        return modelAndView;
    }


    @GetMapping("/add-report")
    public ModelAndView addingBug(Model model){
        if(!model.containsAttribute("reportABugBindingModel")){
            model.addAttribute("reportABugBindingModel", new ReportABugBindingModel());
        }
        return new ModelAndView("report-a-bug");
    }
    @PostMapping("/add-report")
    public ModelAndView addingBugConfirm(@Valid ReportABugBindingModel reportABugBindingModel,
                                         BindingResult bindingResult,
                                         RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("reportABugBindingModel", new ReportABugBindingModel());
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.reportABugBindingModel", bindingResult);
            return new ModelAndView("report-a-bug");
        }


        boolean isCreated = bugsService.create(reportABugBindingModel);
        String view = isCreated ? "redirect:/bugs/thank-you" : "add-report";
        return new ModelAndView(view);

    }

    @GetMapping("/thank-you")
    public String successfulReport(){
        return "thank-you-for-the-report";
    }
    @DeleteMapping("/{uuid}")
    public String delete(@PathVariable("uuid") String uuid){

        bugsService.deleteBug(uuid);

        return "redirect:/bugs/reported-bugs";
    }

}