package com.example.java_spring_advanced_project.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bmw")
public class BmwController {
    @GetMapping("/bmw-cars-home")
    public String reporterBugs() {
        return "BMW-cars";
    }
}
