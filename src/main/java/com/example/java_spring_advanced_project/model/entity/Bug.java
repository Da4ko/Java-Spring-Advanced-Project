package com.example.java_spring_advanced_project.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "bugs")
public class Bug extends BaseEntity {


    private UserEntity reportedBy;
    private String description;

    public Bug() {
    }
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reported_by")
    public UserEntity getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(UserEntity reportedBy) {
        this.reportedBy = reportedBy;
    }

    @Column(nullable = true, columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}