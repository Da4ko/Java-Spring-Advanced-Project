package com.example.java_spring_advanced_project.service.scheduling;

import com.example.java_spring_advanced_project.model.entity.Transmission;
import com.example.java_spring_advanced_project.repository.TransmissionRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
public class TransmissionBackupService {

    private final TransmissionRepository transmissionRepository;
    private static final String BACKUP_DIR = "backups";
    private static final String BACKUP_FILE = BACKUP_DIR + File.separator + "transmissions_backup.csv";

    public TransmissionBackupService(TransmissionRepository transmissionRepository) {
        this.transmissionRepository = transmissionRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    // Runs every 20 seconds
    public void performBackup() {
        List<Transmission> transmissions = transmissionRepository.findAll();
        createBackupDirectoryIfNotExists();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BACKUP_FILE))) {
            // Write CSV header
            writer.write("id,transmission,description\n");

            for (Transmission transmission : transmissions) {
                // Format and write Transmission data
                writer.write(String.format("%s,%s,%s\n",
                        transmission.getId(),
                        transmission.getTransmission() != null ? transmission.getTransmission().name() : "N/A", // Convert enum to string
                        transmission.getDescription() != null ? transmission.getDescription() : "" // Handle possible null
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
