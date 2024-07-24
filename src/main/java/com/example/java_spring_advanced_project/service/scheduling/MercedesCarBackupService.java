package com.example.java_spring_advanced_project.service.scheduling;



import com.example.java_spring_advanced_project.model.entity.MercedesCar;
import com.example.java_spring_advanced_project.repository.MercedesCarsRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
public class MercedesCarBackupService {

    private final MercedesCarsRepository mercedesCarRepository;
    private static final String BACKUP_DIR = "backups";
    private static final String BACKUP_FILE = BACKUP_DIR + File.separator + "mercedes_cars_backup.csv";

    public MercedesCarBackupService(MercedesCarsRepository mercedesCarRepository) {
        this.mercedesCarRepository = mercedesCarRepository;
    }

    @Scheduled(cron = "0 0 */8 * * ?")// Runs daily at midnight
    public void performBackup() {
        List<MercedesCar> mercedesCars = mercedesCarRepository.findAll();
        createBackupDirectoryIfNotExists();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BACKUP_FILE))) {
            // Write CSV header
            writer.write("id,horsepowers,imageUrl,releaseDate,category_id,engine_id,transmission_id,kilometers,price,currency_id,description,mercedes_model_id,owner_id\n");

            for (MercedesCar mercedesCar : mercedesCars) {
                writer.write(String.format("%s,%d,%s,%s,%s,%s,%s,%d,%f,%s,%s,%s,%s\n",
                        mercedesCar.getId(),
                        mercedesCar.getHorsepowers(),
                        mercedesCar.getImageUrl(),
                        mercedesCar.getReleaseDate(),
                        mercedesCar.getCategory() != null ? mercedesCar.getCategory().getId() : "null",
                        mercedesCar.getEngine() != null ? mercedesCar.getEngine().getId() : "null",
                        mercedesCar.getTransmission() != null ? mercedesCar.getTransmission().getId() : "null",
                        mercedesCar.getKilometers(),
                        mercedesCar.getPrice(),
                        mercedesCar.getCurrency() != null ? mercedesCar.getCurrency().getId() : "null",
                        mercedesCar.getDescription(),
                        mercedesCar.getMercedesModel() != null ? mercedesCar.getMercedesModel().getId() : "null",
                        mercedesCar.getOwner() != null ? mercedesCar.getOwner().getId() : "null"
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
