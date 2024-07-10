package com.example.java_spring_advanced_project.model.dto.Bugs;

import com.example.java_spring_advanced_project.model.entity.Bug;

public class BugDto {
    private String id;
    private String reportedBy;
    private String description;

    public BugDto() {
    }

    public BugDto(Bug bug){
        this.id = bug.getId();
        this.reportedBy = bug.getReportedBy().getUsername();
        this.description = bug.getDescription();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
