package com.example.java_spring_advanced_project.service.scheduling;

import com.example.java_spring_advanced_project.model.entity.UserRoleEntity;
import com.example.java_spring_advanced_project.repository.UserRoleRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
public class UserRoleBackupService {

    private final UserRoleRepository userRoleRepository;
    private static final String BACKUP_DIR = "backups";
    private static final String BACKUP_FILE = BACKUP_DIR + File.separator + "user_roles_backup.csv";

    public UserRoleBackupService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?")


    public void performBackup() {
        List<UserRoleEntity> roles = userRoleRepository.findAll();
        createBackupDirectoryIfNotExists();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BACKUP_FILE))) {
            // Write CSV header
            writer.write("id,role\n");

            for (UserRoleEntity role : roles) {
                // Format and write UserRoleEntity data
                writer.write(String.format("%d,%s\n",
                        role.getId(),
                        role.getRole() != null ? role.getRole().name() : "N/A" // Convert enum to string
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
