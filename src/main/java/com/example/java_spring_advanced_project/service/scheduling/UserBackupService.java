package com.example.java_spring_advanced_project.service.scheduling;

import com.example.java_spring_advanced_project.model.entity.UserEntity;
import com.example.java_spring_advanced_project.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;



import java.io.BufferedWriter;
import java.io.File;
@Component
public class UserBackupService {

    private final UserRepository userRepository;
    private static final String BACKUP_DIR = "backups";
    private static final String BACKUP_FILE = BACKUP_DIR + File.separator + "users_backup.csv";

    public UserBackupService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Scheduled(cron = "0 0 * * * ?")
    // Schedules the task to run daily at midnight
    public void performBackup() {
        List<UserEntity> users = userRepository.findAll();
        createBackupDirectoryIfNotExists();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BACKUP_FILE))) {
            // Write CSV header
            writer.write("username,email,password,active\n");

            for (UserEntity user : users) {
                // Format and write user data
                writer.write(String.format("%s,%s,%s,%b\n",
                        user.getUsername(),
                        user.getEmail(),
                        user.getPassword(), // Be cautious with sensitive data
                        user.isActive()
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
