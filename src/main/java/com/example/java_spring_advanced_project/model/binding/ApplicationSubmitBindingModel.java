package com.example.java_spring_advanced_project.model.binding;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ApplicationSubmitBindingModel {
    private String description;

    @NotNull(message = "{bug.report.description.notNull}")
    @Size(min = 2, max = 500, message = "{bug.report.description.size}")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
