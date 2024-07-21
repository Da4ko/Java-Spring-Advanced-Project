package com.example.java_spring_advanced_project.model.binding;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ApplicationSubmitBindingModel {
    private String description;

    @NotNull(message = "{applicationSubmitBindingModel.description.NotNull}")
    @Size(min = 2, max = 500, message = "{applicationSubmitBindingModel.description.Size}")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
