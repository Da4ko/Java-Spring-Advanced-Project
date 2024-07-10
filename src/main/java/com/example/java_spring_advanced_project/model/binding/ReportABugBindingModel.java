package com.example.java_spring_advanced_project.model.binding;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ReportABugBindingModel {
    private String description;
    @NotNull(message = "You must enter the description")
    @Size(min = 2, max=500, message = "Description must be between 2 and 500 characters")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
