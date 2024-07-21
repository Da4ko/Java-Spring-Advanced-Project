package com.example.java_spring_advanced_project.web;

import com.example.java_spring_advanced_project.model.binding.ApplicationSubmitBindingModel;
import com.example.java_spring_advanced_project.model.binding.ReportABugBindingModel;
import com.example.java_spring_advanced_project.model.dto.HomeAdminApplicationDto;
import com.example.java_spring_advanced_project.service.AdminApplicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/apply")
public class AdminApplicationController {

    private final AdminApplicationService adminApplicationService;

    public AdminApplicationController(AdminApplicationService adminApplicationService) {
        this.adminApplicationService = adminApplicationService;
    }



    @GetMapping("/view-applications")
    public ModelAndView viewApplications(){

        ModelAndView modelAndView = new ModelAndView("admin.application");
        HomeAdminApplicationDto applicationsToBeVisualised = adminApplicationService.getApplicationsForBugsHome();
        modelAndView.addObject("homeAdminApplicationDto", applicationsToBeVisualised);

        return modelAndView;
    }


    @GetMapping("/make-application")
    public ModelAndView apply(Model model){
        if(!model.containsAttribute("applicationSubmitBindingModel")){
            model.addAttribute("applicationSubmitBindingModel", new ApplicationSubmitBindingModel());
        }
        return new ModelAndView("apply.for.admin");
    }
    @PostMapping("/make-application")
    public ModelAndView applyConfirm(@Valid ApplicationSubmitBindingModel applicationSubmitBindingModel,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("applicationSubmitBindingModel", new ReportABugBindingModel());
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.applicationSubmitBindingModel", bindingResult);
            return new ModelAndView("apply.for.admin");
        }


        boolean isCreated = adminApplicationService.create(applicationSubmitBindingModel);
        String view = isCreated ? "redirect:/apply/successful-application" : "/make-application";
        return new ModelAndView(view);
    }

    @GetMapping("/successful-application")
    public String successfulReport(){
        return "your-application-has-been-accepted";
    }
    @DeleteMapping("/{uuid}")
    public String delete(@PathVariable("uuid") String uuid){

        adminApplicationService.deleteApplication(uuid);

        return "redirect:/apply/view-applications";
    }
    @DeleteMapping("/accept/{uuid}")
    public String acceptApplication(@PathVariable("uuid") String uuid){

        adminApplicationService.giveAdmin(uuid);

        return "redirect:/apply/view-applications";
    }

}
