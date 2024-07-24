package com.example.java_spring_advanced_project.service.scheduling;


import com.example.java_spring_advanced_project.model.entity.PorscheCar;
import com.example.java_spring_advanced_project.repository.PorscheCarsRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
public class PorscheCarBackupService {

    private final PorscheCarsRepository porscheCarRepository;
    private static final String BACKUP_DIR = "backups";
    private static final String BACKUP_FILE = BACKUP_DIR + File.separator + "porsche_cars_backup.csv";

    public PorscheCarBackupService(PorscheCarsRepository porscheCarRepository) {
        this.porscheCarRepository = porscheCarRepository;
    }

    @Scheduled(cron = "0 0 */8 * * ?") // Runs daily at midnight
    public void performBackup() {
        List<PorscheCar> porscheCars = porscheCarRepository.findAll();
        createBackupDirectoryIfNotExists();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BACKUP_FILE))) {
            // Write CSV header
            writer.write("id,horsepowers,imageUrl,releaseDate,category_id,engine_id,transmission_id,kilometers,price,currency_id,description,porsche_model_id,owner_id\n");

            for (PorscheCar porscheCar : porscheCars) {
                writer.write(String.format("%s,%d,%s,%s,%s,%s,%s,%d,%f,%s,%s,%s,%s\n",
                        porscheCar.getId(),
                        porscheCar.getHorsepowers(),
                        porscheCar.getImageUrl(),
                        porscheCar.getReleaseDate(),
                        porscheCar.getCategory() != null ? porscheCar.getCategory().getId() : "null",
                        porscheCar.getEngine() != null ? porscheCar.getEngine().getId() : "null",
                        porscheCar.getTransmission() != null ? porscheCar.getTransmission().getId() : "null",
                        porscheCar.getKilometers(),
                        porscheCar.getPrice(),
                        porscheCar.getCurrency() != null ? porscheCar.getCurrency().getId() : "null",
                        porscheCar.getDescription(),
                        porscheCar.getPorscheModel() != null ? porscheCar.getPorscheModel().getId() : "null",
                        porscheCar.getOwner() != null ? porscheCar.getOwner().getId() : "null"
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
