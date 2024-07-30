package com.example.java_spring_advanced_project.service.scheduling;


import com.example.java_spring_advanced_project.model.entity.AdminApplication;

import com.example.java_spring_advanced_project.repository.AdminApplicationsRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
public class AdminApplicationsBackupService {

    private final AdminApplicationsRepository adminApplicationRepository;
    private static final String BACKUP_DIR = "backups";
    private static final String BACKUP_FILE = BACKUP_DIR + File.separator + "admin_applications_backup.csv";


    public AdminApplicationsBackupService(AdminApplicationsRepository adminApplicationRepository) {
        this.adminApplicationRepository = adminApplicationRepository;
    }

    @Scheduled(cron = "0 0 */8 * * ?")// Runs daily at midnight
    public void performBackup() {
        List<AdminApplication> applications = adminApplicationRepository.findAll();
        createBackupDirectoryIfNotExists();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BACKUP_FILE))) {
            // Write CSV header
            writer.write("id,applicant_id,applicant_username,description\n");

            for (AdminApplication application : applications) {
                String applicantUsername = application.getApplicant() != null ? application.getApplicant().getUsername() : "null";
                writer.write(String.format("%s,%s,%s,%s\n",
                        application.getId(),
                        application.getApplicant() != null ? application.getApplicant().getId() : "null",
                        applicantUsername,
                        application.getDescription() != null ? application.getDescription() : "null"
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createBackupDirectoryIfNotExists() {
        File backupDir = new File(BACKUP_DIR);
        if (!backupDir.exists()) {
            backupDir.mkdirs(); // Create the directory if it does not exist
        }
    }
}
