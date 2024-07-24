package com.example.java_spring_advanced_project.service.scheduling;

import com.example.java_spring_advanced_project.model.entity.Category;
import com.example.java_spring_advanced_project.repository.CategoryRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
public class CategoryBackupService {

    private final CategoryRepository categoryRepository;
    private static final String BACKUP_DIR = "backups";
    private static final String BACKUP_FILE = BACKUP_DIR + File.separator + "categories_backup.csv";

    public CategoryBackupService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    // Runs every 20 seconds
    public void performBackup() {
        List<Category> categories = categoryRepository.findAll();
        createBackupDirectoryIfNotExists();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BACKUP_FILE))) {
            // Write CSV header
            writer.write("id,category,description\n");

            for (Category category : categories) {
                // Format and write category data
                writer.write(String.format("%s,%s,%s\n",
                        category.getId(),
                        category.getCategory() != null ? category.getCategory().name() : "N/A", // Convert enum to string
                        category.getDescription() != null ? category.getDescription() : "" // Handle possible null
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
