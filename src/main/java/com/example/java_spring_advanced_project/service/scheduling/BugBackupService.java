package com.example.java_spring_advanced_project.service.scheduling;


import com.example.java_spring_advanced_project.model.entity.Bug;
import com.example.java_spring_advanced_project.repository.BugsRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
public class BugBackupService {

    private final BugsRepository bugRepository;
    private static final String BACKUP_DIR = "backups";
    private static final String BACKUP_FILE = BACKUP_DIR + File.separator + "bugs_backup.csv";

    public BugBackupService(BugsRepository bugRepository) {
        this.bugRepository = bugRepository;
    }

    @Scheduled(cron = "0 0 */8 * * ?")// Runs every 20 seconds
    public void performBackup() {
        List<Bug> bugs = bugRepository.findAll();
        createBackupDirectoryIfNotExists();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BACKUP_FILE))) {
            // Write CSV header
            writer.write("id,reported_by,description\n");

            for (Bug bug : bugs) {
                // Format and write bug data
                writer.write(String.format("%s,%s,%s\n",
                        bug.getId(),
                        bug.getReportedBy() != null ? bug.getReportedBy().getId() : "N/A", // Handle possible null
                        bug.getDescription() != null ? bug.getDescription() : "" // Handle possible null
                ));
            }
        } catch (IOException e) {
            System.err.println("Error during backup: " + e.getMessage());
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
