package com.example.java_spring_advanced_project.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "applications")
public class AdminApplication extends BaseEntity{

    private UserEntity applicant;
    private String description;

    public AdminApplication() {
    }
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "applicant_id")
    public UserEntity getApplicant() {
        return applicant;
    }

    public void setApplicant(UserEntity applicant) {
        this.applicant = applicant;
    }
    @Column(nullable = true, columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
