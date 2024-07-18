package com.example.java_spring_advanced_project.model.binding;

import com.example.java_spring_advanced_project.validation.anotations.UniqueUsername;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ChangeUsernameBindingModel {


    @NotBlank(message = "Username cannot be empty String")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters" )
    @UniqueUsername(message = "You must select username that hasn't been taken")
    private String newUserName;

    public ChangeUsernameBindingModel() {
    }

    public String getNewUserName() {
        return newUserName;
    }

    public void setNewUserName(String newUserName) {
        this.newUserName = newUserName;
    }
}
