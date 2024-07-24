package com.example.java_spring_advanced_project.service.scheduling;


import com.example.java_spring_advanced_project.model.entity.AudiCar;
import com.example.java_spring_advanced_project.repository.AudiCarsRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
public class AudiCarBackupService {

    private final AudiCarsRepository audiCarsRepository;
    private static final String BACKUP_DIR = "backups";
    private static final String BACKUP_FILE = BACKUP_DIR + File.separator + "audi_cars_backup.csv";

    public AudiCarBackupService(AudiCarsRepository audiCarsRepository) {
        this.audiCarsRepository = audiCarsRepository;
    }

    @Scheduled(cron = "0 0 */8 * * ?")// Runs daily at midnight
    public void performBackup() {
        List<AudiCar> audiCars = audiCarsRepository.findAll();
        createBackupDirectoryIfNotExists();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BACKUP_FILE))) {
            // Write CSV header
            writer.write("id,horsepowers,imageUrl,releaseDate,category_id,engine_id,transmission_id,kilometers,price,currency_id,description,audi_model_id,owner_id\n");

            for (AudiCar audiCar : audiCars) {
                writer.write(String.format("%s,%d,%s,%s,%s,%s,%s,%s,%f,%s,%s,%s,%s\n",
                        audiCar.getId(),
                        audiCar.getHorsepowers(),
                        audiCar.getImageUrl(),
                        audiCar.getReleaseDate(),
                        audiCar.getCategory() != null ? audiCar.getCategory().getId() : "null",
                        audiCar.getEngine() != null ? audiCar.getEngine().getId() : "null",
                        audiCar.getTransmission() != null ? audiCar.getTransmission().getId() : "null",
                        audiCar.getKilometers(),
                        audiCar.getPrice(),
                        audiCar.getCurrency() != null ? audiCar.getCurrency().getId() : "null",
                        audiCar.getDescription(),
                        audiCar.getAudiModel() != null ? audiCar.getAudiModel().getId() : "null",
                        audiCar.getOwner() != null ? audiCar.getOwner().getId() : "null"
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
