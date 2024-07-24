package com.example.java_spring_advanced_project.service.scheduling;

import com.example.java_spring_advanced_project.model.entity.PorscheModel;
import com.example.java_spring_advanced_project.repository.PorscheModelRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
public class PorscheModelBackupService {

    private final PorscheModelRepository porscheModelRepository;
    private static final String BACKUP_DIR = "backups";
    private static final String BACKUP_FILE = BACKUP_DIR + File.separator + "porsche_models_backup.csv";

    public PorscheModelBackupService(PorscheModelRepository porscheModelRepository) {
        this.porscheModelRepository = porscheModelRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    // Runs every 20 seconds
    public void performBackup() {
        List<PorscheModel> porscheModels = porscheModelRepository.findAll();
        createBackupDirectoryIfNotExists();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BACKUP_FILE))) {
            // Write CSV header
            writer.write("id,model,description\n");

            for (PorscheModel porscheModel : porscheModels) {
                // Format and write PorscheModel data
                writer.write(String.format("%s,%s,%s\n",
                        porscheModel.getId(),
                        porscheModel.getModel() != null ? porscheModel.getModel().name() : "N/A", // Convert enum to string
                        porscheModel.getDescription() != null ? porscheModel.getDescription() : "" // Handle possible null
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
