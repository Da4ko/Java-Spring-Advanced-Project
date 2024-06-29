package com.example.java_spring_advanced_project.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bugs")
public class BugController {
    @GetMapping("/reported-bugs")
    public String reporterBugs() {
        return "bugs";
    }
}
