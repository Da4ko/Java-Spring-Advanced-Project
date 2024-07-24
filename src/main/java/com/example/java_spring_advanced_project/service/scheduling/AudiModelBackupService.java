package com.example.java_spring_advanced_project.service.scheduling;



import com.example.java_spring_advanced_project.model.entity.AudiModel;
import com.example.java_spring_advanced_project.repository.AudiModelRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
public class AudiModelBackupService {

    private final AudiModelRepository audiModelRepository;
    private static final String BACKUP_DIR = "backups";
    private static final String BACKUP_FILE = BACKUP_DIR + File.separator + "audi_models_backup.csv";

    public AudiModelBackupService(AudiModelRepository audiModelRepository) {
        this.audiModelRepository = audiModelRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    // Runs every 20 seconds
    public void performBackup() {
        List<AudiModel> audiModels = audiModelRepository.findAll();
        createBackupDirectoryIfNotExists();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BACKUP_FILE))) {
            // Write CSV header
            writer.write("id,model,description\n");

            for (AudiModel audiModel : audiModels) {
                // Format and write audiModel data
                writer.write(String.format("%s,%s,%s\n",
                        audiModel.getId(),
                        audiModel.getModel() != null ? audiModel.getModel().name() : "N/A", // Convert enum to string
                        audiModel.getDescription() != null ? audiModel.getDescription() : "" // Handle possible null
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
