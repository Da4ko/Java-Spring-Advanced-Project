package com.example.java_spring_advanced_project.web;

import com.example.java_spring_advanced_project.model.binding.ChangeUsernameBindingModel;
import com.example.java_spring_advanced_project.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

@Controller
public class HomeController {
    private final UserService userService;
    private final MessageSource messageSource;
@Autowired
    public HomeController(UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @GetMapping("/")
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String && ((String) authentication.getPrincipal()).equals("anonymousUser"));

        if (isAuthenticated) {
            model.addAttribute("changeUsernameBindingModel", new ChangeUsernameBindingModel()); // Add the required model attribute
            return "home_logged_in";
        } else {
            return "home_not_logged_in";
        }
    }

    @GetMapping("/home")
    public String home(Model model){
        if(!model.containsAttribute("changeUsernameBindingModel")){
            model.addAttribute("changeUsernameBindingModel", new ChangeUsernameBindingModel());
        }

        return "home_logged_in";
    }
    @PostMapping("/home")
    public ModelAndView changeUsername(@Valid ChangeUsernameBindingModel changeUsernameBindingModel,
                                       BindingResult bindingResult,
                                       RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("changeUsernameBindingModel", new ChangeUsernameBindingModel());
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.changeUsernameBindingModel", bindingResult);
            return new ModelAndView("home_logged_in");
        }
        boolean isCreated = userService.changeUsername(changeUsernameBindingModel);
        String view = isCreated ? "redirect:/changed-username" : "home_logged_in";

        return new ModelAndView(view);
    }


  @GetMapping("/about-us")
  public String aboutUs(){
      return "about-us";
  }
    @GetMapping("/changed-username")
    public String ChangedUsernameMessage(){
        return "changed-username-page";
    }

}
