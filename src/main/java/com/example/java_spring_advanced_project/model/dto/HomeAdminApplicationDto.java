package com.example.java_spring_advanced_project.model.dto;

import com.example.java_spring_advanced_project.model.dto.Applications.ApplicationDto;
import com.example.java_spring_advanced_project.model.entity.AdminApplication;

import java.util.List;

public class HomeAdminApplicationDto {
    private List<ApplicationDto> adminApplications;

    public HomeAdminApplicationDto() {
    }



    public HomeAdminApplicationDto(List<ApplicationDto> adminApplications) {
        this.adminApplications = adminApplications;
    }

    public List<ApplicationDto> getApplicationsToBeVisualised() {
        return adminApplications;
    }

    public void setApplicationsToBeVisualised(List<ApplicationDto> adminApplications) {
        this.adminApplications = adminApplications;
    }
}
