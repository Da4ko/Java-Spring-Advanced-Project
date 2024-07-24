package com.example.java_spring_advanced_project.service.scheduling;



import com.example.java_spring_advanced_project.model.entity.BmwCar;
import com.example.java_spring_advanced_project.repository.BmwCarsRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
public class BmwCarBackupService {

    private final BmwCarsRepository bmwCarsRepository;
    private static final String BACKUP_DIR = "backups";
    private static final String BACKUP_FILE = BACKUP_DIR + File.separator + "bmw_cars_backup.csv";

    public BmwCarBackupService(BmwCarsRepository bmwCarsRepository) {
        this.bmwCarsRepository = bmwCarsRepository;
    }


    @Scheduled(cron = "0 0 */8 * * ?")// Runs daily at midnight
    public void performBackup() {
        List<BmwCar> bmwCars = bmwCarsRepository.findAll();
        createBackupDirectoryIfNotExists();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BACKUP_FILE))) {
            // Write CSV header
            writer.write("id,horsepowers,imageUrl,releaseDate,category_id,engine_id,transmission_id,kilometers,price,currency_id,description,bmw_model_id,owner_id\n");

            for (BmwCar bmwCar : bmwCars) {
                writer.write(String.format("%s,%d,%s,%s,%s,%s,%s,%d,%f,%s,%s,%s,%s\n",
                        bmwCar.getId(),
                        bmwCar.getHorsepowers(),
                        bmwCar.getImageUrl(),
                        bmwCar.getReleaseDate(),
                        bmwCar.getCategory() != null ? bmwCar.getCategory().getId() : "null",
                        bmwCar.getEngine() != null ? bmwCar.getEngine().getId() : "null",
                        bmwCar.getTransmission() != null ? bmwCar.getTransmission().getId() : "null",
                        bmwCar.getKilometers(),
                        bmwCar.getPrice(),
                        bmwCar.getCurrency() != null ? bmwCar.getCurrency().getId() : "null",
                        bmwCar.getDescription(),
                        bmwCar.getBmwModel() != null ? bmwCar.getBmwModel().getId() : "null",
                        bmwCar.getOwner() != null ? bmwCar.getOwner().getId() : "null"
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
