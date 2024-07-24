package com.example.java_spring_advanced_project.service.scheduling;

import com.example.java_spring_advanced_project.model.entity.Currency;
import com.example.java_spring_advanced_project.repository.CurrencyRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
public class CurrencyBackupService {

    private final CurrencyRepository currencyRepository;
    private static final String BACKUP_DIR = "backups";
    private static final String BACKUP_FILE = BACKUP_DIR + File.separator + "currencies_backup.csv";

    public CurrencyBackupService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    // Runs every 20 seconds
    public void performBackup() {
        List<Currency> currencies = currencyRepository.findAll();
        createBackupDirectoryIfNotExists();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BACKUP_FILE))) {
            // Write CSV header
            writer.write("id,currency,originCountry,toEuro\n");

            for (Currency currency : currencies) {
                // Format and write currency data
                writer.write(String.format("%s,%s,%s,%f\n",
                        currency.getId(),
                        currency.getCurrency() != null ? currency.getCurrency().name() : "N/A", // Convert enum to string
                        currency.getOriginCountry() != null ? currency.getOriginCountry() : "", // Handle possible null
                        currency.getToEuro()
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
