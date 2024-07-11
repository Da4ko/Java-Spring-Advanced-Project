package com.example.java_spring_advanced_project.service;

import com.example.java_spring_advanced_project.model.binding.ChangeUsernameBindingModel;
import com.example.java_spring_advanced_project.model.binding.UserRegisterBindingModel;

public interface UserService {
    void register(UserRegisterBindingModel userRegisterBindingModel);

    boolean changeUsername(ChangeUsernameBindingModel changeUsernameBindingModel);
}
