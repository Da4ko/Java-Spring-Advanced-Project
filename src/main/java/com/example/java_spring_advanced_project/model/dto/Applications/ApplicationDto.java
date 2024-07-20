package com.example.java_spring_advanced_project.model.dto.Applications;

import com.example.java_spring_advanced_project.model.entity.AdminApplication;
import com.example.java_spring_advanced_project.model.entity.Bug;

public class ApplicationDto {
    private String id;
    private String applicant;
    private String description;

    public ApplicationDto() {
    }
    public ApplicationDto(AdminApplication application ){
        this.id = application.getId();
        this.applicant = application.getApplicant().getUsername();
        this.description = application.getDescription();
    }

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
