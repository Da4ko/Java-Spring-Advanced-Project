package com.example.java_spring_advanced_project.web;

import com.example.java_spring_advanced_project.service.AdminApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/apply")
public class AdminApplicationController {

    private final AdminApplicationService adminApplicationService;

    public AdminApplicationController(AdminApplicationService adminApplicationService) {
        this.adminApplicationService = adminApplicationService;
    }


    @GetMapping("/make-application")
    public ModelAndView apply(){
        return new ModelAndView("apply.for.admin");
    }
    @GetMapping("/view-applications")
    public ModelAndView viewApplications(){
        return new ModelAndView("admin.application");
    }
}
