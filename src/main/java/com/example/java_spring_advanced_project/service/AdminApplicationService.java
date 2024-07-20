package com.example.java_spring_advanced_project.service;

import com.example.java_spring_advanced_project.model.binding.ApplicationSubmitBindingModel;
import com.example.java_spring_advanced_project.model.binding.ReportABugBindingModel;
import com.example.java_spring_advanced_project.model.dto.HomeAdminApplicationDto;
import com.example.java_spring_advanced_project.model.dto.HomeBugsDto;

public interface AdminApplicationService {
    HomeAdminApplicationDto getApplicationsForBugsHome();

    boolean create(ApplicationSubmitBindingModel applicationSubmitBindingModel);

    void deleteApplication(String uuid);
    void giveAdmin(String uuid);
}
