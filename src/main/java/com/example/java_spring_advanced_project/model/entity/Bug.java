package com.example.java_spring_advanced_project.model.entity;

import com.example.java_spring_advanced_project.model.entity.enums.BugType;
import jakarta.persistence.*;

@Entity
@Table(name = "bugs")
public class Bug extends BaseEntity {


    private User reportedBy;


    private BugType bugType;


    private String description;

    public Bug() {
    }
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reported_by")
    public User getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(User reportedBy) {
        this.reportedBy = reportedBy;
    }
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public BugType getBugType() {
        return bugType;
    }

    public void setBugType(BugType bugType) {
        this.bugType = bugType;
    }
    @Column(nullable = true, columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}