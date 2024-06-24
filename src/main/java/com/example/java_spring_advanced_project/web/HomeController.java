package com.example.java_spring_advanced_project.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class HomeController {
    @GetMapping("/")
    public String index(){
        return "home_not_logged_in";
    }
}
