package com.example.java_spring_advanced_project.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mercedes")
public class MercedesController {
    @GetMapping("/mercedes-cars-home")
    public String reporterBugs() {
        return "mercedes.cars";
    }
}
