package com.example.java_spring_advanced_project.service.scheduling;

import com.example.java_spring_advanced_project.model.entity.MercedesModel;
import com.example.java_spring_advanced_project.repository.MercedesModelRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
public class MercedesModelBackupService {

    private final MercedesModelRepository mercedesModelRepository;
    private static final String BACKUP_DIR = "backups";
    private static final String BACKUP_FILE = BACKUP_DIR + File.separator + "mercedes_models_backup.csv";

    public MercedesModelBackupService(MercedesModelRepository mercedesModelRepository) {
        this.mercedesModelRepository = mercedesModelRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    // Runs every 20 seconds
    public void performBackup() {
        List<MercedesModel> mercedesModels = mercedesModelRepository.findAll();
        createBackupDirectoryIfNotExists();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BACKUP_FILE))) {
            // Write CSV header
            writer.write("id,model,description\n");

            for (MercedesModel mercedesModel : mercedesModels) {
                // Format and write MercedesModel data
                writer.write(String.format("%s,%s,%s\n",
                        mercedesModel.getId(),
                        mercedesModel.getModel() != null ? mercedesModel.getModel().name() : "N/A", // Convert enum to string
                        mercedesModel.getDescription() != null ? mercedesModel.getDescription() : "" // Handle possible null
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
