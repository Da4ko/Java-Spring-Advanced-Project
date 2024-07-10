package com.example.java_spring_advanced_project.model.dto;

import com.example.java_spring_advanced_project.model.dto.Bugs.BugDto;

import java.util.List;

public class HomeBugsDto {
    private List<BugDto> bugsToBeVisualised;

    public HomeBugsDto() {
    }

    public HomeBugsDto(List<BugDto> bugsToBeVisualised) {
        this.bugsToBeVisualised = bugsToBeVisualised;
    }

    public List<BugDto> getBugsToBeVisualised() {
        return bugsToBeVisualised;
    }

    public void setBugsToBeVisualised(List<BugDto> bugsToBeVisualised) {
        this.bugsToBeVisualised = bugsToBeVisualised;
    }
}
