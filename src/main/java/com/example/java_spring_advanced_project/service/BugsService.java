package com.example.java_spring_advanced_project.service;

import com.example.java_spring_advanced_project.model.binding.ReportABugBindingModel;
import com.example.java_spring_advanced_project.model.dto.HomeBugsDto;

public interface BugsService {
    HomeBugsDto getBugsForBugsHome();

    boolean create(ReportABugBindingModel reportABugBindingModel);

    void deleteBug(String uuid);
}
