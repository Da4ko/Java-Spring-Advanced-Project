package com.example.java_spring_advanced_project.validation.validators;

import com.example.java_spring_advanced_project.repository.UserRepository;
import com.example.java_spring_advanced_project.validation.anotations.UniqueUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {
        // You can add any initialization code here if needed
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (userRepository == null) {
            return true;  // UserRepository is not available, skip validation
        }
        return !userRepository.existsByUsername(username);
    }
}
