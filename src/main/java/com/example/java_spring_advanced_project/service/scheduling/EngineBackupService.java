package com.example.java_spring_advanced_project.service.scheduling;

import com.example.java_spring_advanced_project.model.entity.Engine;
import com.example.java_spring_advanced_project.repository.EngineRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
public class EngineBackupService {

    private final EngineRepository engineRepository;
    private static final String BACKUP_DIR = "backups";
    private static final String BACKUP_FILE = BACKUP_DIR + File.separator + "engines_backup.csv";

    public EngineBackupService(EngineRepository engineRepository) {
        this.engineRepository = engineRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    // Runs every 20 seconds
    public void performBackup() {
        List<Engine> engines = engineRepository.findAll();
        createBackupDirectoryIfNotExists();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BACKUP_FILE))) {
            // Write CSV header
            writer.write("id,engineType,description\n");

            for (Engine engine : engines) {
                // Format and write engine data
                writer.write(String.format("%s,%s,%s\n",
                        engine.getId(),
                        engine.getEngineType() != null ? engine.getEngineType().name() : "N/A", // Convert enum to string
                        engine.getDescription() != null ? engine.getDescription() : "" // Handle possible null
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
