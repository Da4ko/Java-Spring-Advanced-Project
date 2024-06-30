package com.example.java_spring_advanced_project.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index(){
        return "home_not_logged_in";
    }
    /*@GetMapping("/")
    public String index() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Check if user is authenticated
        if (auth != null && auth.isAuthenticated()) {
            return "home_logged_in"; // Return the logged-in view
        } else {
            return "home_not_logged_in"; // Return the not logged-in view
        }
    }*/
    @GetMapping("/home")
    public String home(){
        return "home_logged_in"; //home_logged_in
    }
  /*  @GetMapping("/test/logout")
    public String index2(){
        return "home_not_logged_in"; //login
    }*/
  @GetMapping("/about-us")
  public String aboutUs(){
      return "about-us"; //home_logged_in
  }

}
